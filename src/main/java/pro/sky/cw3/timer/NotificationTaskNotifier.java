package pro.sky.cw3.timer;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.cw3.repository.NotificationTaskRepository;
import pro.sky.cw3.service.TelegramBotService;


@Component
public class NotificationTaskNotifier {



    private final NotificationTaskRepository notificationTaskRepository;

    private final TelegramBotService telegramBotService;

    public NotificationTaskNotifier(NotificationTaskRepository notificationTaskRepository, TelegramBotService telegramBotService) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.telegramBotService = telegramBotService;
    }


    @Scheduled(timeUnit = TimeUnit.MINUTES,fixedDelay = 1)
    @Transactional
    public void task() {
        notificationTaskRepository.findAllByDateTime(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        ).forEach(notificationTask -> {
            telegramBotService.sendMessage(
                    notificationTask.getChatId(),
                    "Вы просили напомнить о сообщении:" + notificationTask.getText()
            );
            notificationTaskRepository.delete(notificationTask);
        });
    }
}
