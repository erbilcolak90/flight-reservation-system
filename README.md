# Flight-reservation-system

Bu program, bir uçuş rezervasyon sistemini simüle etmektedir. Kullanıcılar uçuşları görüntüleyebilir, rezervasyon yapabilir ve mevcut rezervasyonlarını yönetebilir. Ayrıca admin kullanıcıları uçuşlar, rezervasyonlar ve kullanıcılar üzerinde yönetim işlemleri gerçekleştirebilir.

## Projeye Genel Bakış

Bu sistem, Spring Boot kullanılarak geliştirilmiştir ve MySQL veritabanı kullanır. Ayrıca, Docker ile konteynerleştirilmiştir, bu sayede tüm bağımlılıklar izole edilmiş bir ortamda çalıştırılabilir.

https://github.com/erbilcolak90/flight-reservation-system

## Başlangıç

### Gerekli Araçlar
- Docker
- Docker Compose
- Java 11 veya üstü
- IDE (IntelliJ IDEA, Eclipse vb.)

### Teknolojiler

- **Spring Boot**: Uygulama backend geliştirmesi için
- **MySQL**: Veritabanı yönetimi için
- **Docker**: Uygulama ve veritabanı konteynerizasyonu için
- **Swagger**: API dokümantasyonu ve test arayüzü için


### Çalıştırma Adımları

1. **Docker Compose Çalıştırma**  
   Projeyi başlatmadan önce, öncelikle MySQL veritabanı konteyneri için gerekli olan Docker Compose komutunu çalıştırın. Aşağıdaki komut ile Docker konteynerlerini başlatabilirsiniz:

   ```bash
   docker-compose up -d


Bu komut, gerekli MySQL veritabanı konteynerini başlatacaktır. Ek olarak database dosyası proje dizinindeki db dosyası içerisinde mevcuttur.

Projeyi IDE Üzerinden Çalıştırma
Docker Compose işlemi tamamlandıktan sonra, Spring Boot projesini IDE üzerinden çalıştırın. IDE üzerinde projenizi başlatarak uçuş rezervasyon sistemine erişebilirsiniz.
Authorization Bilgileri
Uygulama giriş işlemleri için aşağıdaki kullanıcı bilgilerini kullanabilirsiniz:

### Authorization Bilgileri

Aşağıdaki kullanıcı bilgilerini kullanarak sisteme giriş yapabilirsiniz:

- **Admin Kullanıcı:**
    - **Email:** `admin`
    - **Password:** `123123`

- **User Kullanıcı:**
    - **Email:** `user`
    - **Password:** `123456`


Swagger ile API'ye Erişim
Swagger üzerinden API arayüzüne erişmek için şu URL'yi kullanabilirsiniz:

bash
Kodu kopyala
http://localhost:8080/swagger-ui/index.html#/

Bu arayüz üzerinden API'ye erişebilir, login işlemini gerçekleştirebilir ve input/payload şemalarını görebilirsiniz.

API Erişimi ve Giriş Yapma
Swagger UI üzerinden login işlemi yapabilirsiniz. Kullanıcı bilgilerinizi girdikten sonra, sistem size bir JWT token verecektir. Bu token'ı, sistemin diğer API'lerine erişim sağlamak için kullanabilirsiniz.

API çağrıları sırasında, Swagger UI üzerinde her API için gerekli parametreler ve payload şemaları da sağlanmıştır.

Veritabanı Yapısı
Proje, uçuşlar, rezervasyonlar, kullanıcılar ve roller gibi varlıkları yönetir. Aşağıda temel veritabanı tablolarının yapısı özetlenmiştir:

Flights: Uçuşların detaylarını içerir.
Seats: Her uçuş için koltuk bilgilerini içerir.
Reservations: Kullanıcıların yaptıkları rezervasyonları içerir.
Orders: Yapılan rezervasyonların sipariş bilgilerini içerir.
Users: Sistemdeki kullanıcı bilgilerini içerir.
Roles: Kullanıcı rollerini içerir (Admin, User vb.).
Test Verileri
Veritabanını test verileriyle doldurmak için init_db.sql dosyasını kullanabilirsiniz.

Kullanıcı Rollerinin Yönetimi
Admin: Uçuşlar, rezervasyonlar, kullanıcılar ve roller üzerinde yönetim yetkilerine sahiptir.
User: Sadece kendi rezervasyonlarını yapabilir ve görüntüleyebilir.