package com.fho.digitalpec.job;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.animalvaccine.entity.NextApplicationDate;
import com.fho.digitalpec.api.animalvaccine.service.NextApplicationDateService;
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

    private final NextApplicationDateService nextApplicationDateService;
    private final NotificationService notificationService;

    //    @Scheduled(cron = "0 0 3 * * ?", zone = "UTC")
    @Scheduled(cron = "0/60 * * * * MON-FRI", zone = "UTC")
    public void run() {
        log.info("[Task start] Sending vaccine application reminder notifications.");

        try {
            List<NextApplicationDate> nextApplicationDates = nextApplicationDateService.findNextApplicationDates();

            nextApplicationDates.forEach(this::sendReminderNotification);

        } catch (Exception e) {
            log.error("Error occurred while sending vaccine application reminder notifications.", e);
        }

        log.info("[Task end] Sending vaccine application reminder notifications.");
    }

    private void sendReminderNotification(NextApplicationDate nextApplicationDate) {
        try {
            User targetUser = nextApplicationDate.getAnimalVaccine().getAnimal().getUser();
            String message = formatMessage(nextApplicationDate);

            Notification notification = Notification.builder()
                    .title("Lembrete de vacinação próxima")
                    .message(message)
                    .user(targetUser)
                    .build();

            notificationService.sendSendVaccineApplicationReminder(notification);
            log.info("Notification sent to user: {}", targetUser.getId());
        } catch (Exception e) {
            log.error("Failed to send notification for application date: {}", nextApplicationDate, e);
        }
    }

    private String formatMessage(NextApplicationDate nextApplicationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Animal animal = nextApplicationDate.getAnimalVaccine().getAnimal();
        String vaccineName = nextApplicationDate.getAnimalVaccine().getVaccine().getName();
        String unitName = animal.getUnit().getName();
        String nextApplicationDates = nextApplicationDate.getAnimalVaccine().getNextApplicationDates().stream()
                .map(NextApplicationDate::getApplicationDate)
                .map(date -> date.format(formatter))
                .collect(Collectors.joining(", "));

        return String.format("O seu animal %s (%s) em %s está agendado para as seguintes vacinas: %s. Certifique-se de administrar a vacina: %s.",
                animal.getName(),
                animal.getIdentification(),
                unitName,
                nextApplicationDates,
                vaccineName);
    }
}

// nextApplicationDates.forEach(a -> {
//         User targetUser = a.getAnimalVaccine().getAnimal().getUser();
//
//         String message = String.format("Vacinação próxima. Vacina: %s, animal: %s, unidade: %s, próximas aplicações: %s.",
//         a.getAnimalVaccine().getVaccine().getName(),
//         a.getAnimalVaccine().getAnimal().getName(),
//         a.getAnimalVaccine().getAnimal().getUnit().getName(),
//         a.getAnimalVaccine().getNextApplicationDates().stream()
//         .map(NextApplicationDate::getApplicationDate)
//         .map(b -> {
//         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//         return b.format(formatter);
//         })
//         .collect(Collectors.joining(","))
//         );
//
//         Notification notification = Notification.builder()
//         .title("Lembrete de vacinação próxima")
//         .message(message)
//         .user(targetUser)
//         .build();
//
//         notificationService.sendSendVaccineApplicationReminder(notification);
//         });