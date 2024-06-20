package com.fho.digitalpec.job;

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

    @Scheduled(cron = "0 0 3 * * ?", zone = "UTC")
    public void run() {
        log.info("[Task start] Sending vaccine application reminder notifications.");

        try {
            List<AnimalVaccine> animalVaccines = animalVaccineService.findNextApplicationDates();

            animalVaccines.forEach(this::sendReminderNotification);

        } catch (Exception e) {
            log.error("Error occurred while sending vaccine application reminder notifications.", e);
        }

        log.info("[Task end] Sending vaccine application reminder notifications.");
    }

    private void sendReminderNotification(AnimalVaccine animalVaccine) {
        try {
            User targetUser = animalVaccine.getAnimal().getUser();
            String message = formatMessage(animalVaccine);

            Notification notification = Notification.builder()
                    .title("Lembrete de vacinação próxima")
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

        return String.format("O seu animal %s (%s) em %s está agendado para as seguintes vacinas: %s. Certifique-se de administrar a vacina: %s.", // todo: internationalize
                animal.getName(),
                animal.getIdentification(),
                unitName,
                nextApplicationDates,
                vaccineName);
    }
}
