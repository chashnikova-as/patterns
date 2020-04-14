import com.github.javafaker.Faker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * класс для работы с тестовыми данными
 */

public class ClaimData {
    Faker faker = new Faker(new Locale("ru"));
    private String city;
    private String dateMeeting;
    private String name;
    private String phone;
    private boolean agreement;

    public String getCity() {
        return city;
    }

    public String getDateMeeting() {
        return dateMeeting;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public boolean getAgreement() {
        return agreement;
    }

    public ClaimData setCity(String city) {
        this.city = city;
        return this;
    }

    public ClaimData setDateMeeting(String dateMeeting) {
        this.dateMeeting = dateMeeting;
        return this;
    }

    public ClaimData setName(String name) {
        this.name = name;
        return this;
    }

    public ClaimData setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public ClaimData setAgreement(boolean agreement) {
        this.agreement = agreement;
        return this;
    }

    /**
     * генерирует все обязательные для заполнения заявки данные пользователя
     *
     * @return данные пользователя для заявки
     */
    public ClaimData generateAllClaimData() {
        String name = faker.name().lastName() + " " + faker.name().firstName();
        String phone = "+7" + faker.phoneNumber().subscriberNumber(10);
        return new ClaimData()
                .setCity(faker.address().cityName())
                .setDateMeeting(generateMeetingDate())
                .setName(name)
                .setPhone(phone)
                .setAgreement(true);
    }

    /**
     * Получение даты встречи с представителем банка
     *
     * @param days - через сколько дней с текущей даты запланирована встреча
     * @return дата встречи
     */
    public String generateMeetingDate(int days) {
        DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.DAY_OF_MONTH, days);
        return sdf.format(c.getTime());
    }

    /**
     * Получение валидной даты встречи с представителем банка
     *
     * @return дата встречи
     */
    public String generateMeetingDate() {
        DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.DAY_OF_MONTH, rnd(3, 100));
        return sdf.format(c.getTime());
    }

    /**
     * Метод получения псевдослучайного целого числа от min до max (включая max);
     */
    private static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
