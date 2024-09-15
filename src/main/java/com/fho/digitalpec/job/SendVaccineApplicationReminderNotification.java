package com.fho.digitalpec.job;

import static com.fho.digitalpec.utils.language.LanguageUtils.getMessage;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import com.fho.digitalpec.api.animalvaccine.entity.NextApplicationDate;
import com.fho.digitalpec.api.animalvaccine.service.AnimalVaccineService;
import com.fho.digitalpec.api.notification.entity.Notification;
import com.fho.digitalpec.api.notification.service.NotificationService;
import com.fho.digitalpec.api.user.entity.User;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendVaccineApplicationReminderNotification {

    private final AnimalVaccineService animalVaccineService;
    private final NotificationService notificationService;
    private final MessageSource messageSource;

    @Scheduled(cron = "0 0 3 * * ?", zone = "UTC")
    public void run() {
        log.info("[Task start] Sending vaccine application reminder notifications.");

        try {
            List<AnimalVaccine> animalVaccines = animalVaccineService.findNextApplicationDates();

            animalVaccines.forEach(av -> log.info("Sending vaccine application reminder notifications to AnimalVaccine: ID: {}; Next application dates: {}.",
                    av.getId(), av.getNextApplicationDates().stream().map(NextApplicationDate::getApplicationDate).toList()));

            animalVaccines.forEach(this::sendReminderNotification);

            log.info("[Task end] Sending vaccine application reminder notifications. Sent to '{}' animalVaccines", animalVaccines.size());
        } catch (Exception e) {
            log.error("Error occurred while sending vaccine application reminder notifications.", e);
        }
    }

    private void sendReminderNotification(AnimalVaccine animalVaccine) {
        try {
            User targetUser = animalVaccine.getAnimal().getUser();
            String message = formatMessage(animalVaccine);

            Notification notification = Notification.builder()
                    .title(getMessage(messageSource, "notification.title"))
                    .message(message)
                    .user(targetUser)
                    .build();

            notificationService.sendSendVaccineApplicationReminder(notification);
            log.info("Notification sent to user: {}", targetUser.getId());
        } catch (Exception e) {
            log.error("Failed to send notification for application date: {}", animalVaccine, e);
        }
    }

    private String formatMessage(AnimalVaccine animalVaccine) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Animal animal = animalVaccine.getAnimal();
        String vaccineName = animalVaccine.getVaccine().getName();
        String unitName = animal.getUnit().getName();
        String nextApplicationDates = animalVaccine.getNextApplicationDates().stream()
                .map(NextApplicationDate::getApplicationDate)
                .map(date -> date.format(formatter))
                .collect(Collectors.joining(", "));

        return getMessage(messageSource, "notification.message",
                animal.getName(),
                animal.getIdentification(),
                unitName,
                nextApplicationDates,
                vaccineName);
    }
}
