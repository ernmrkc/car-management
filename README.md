
# Car Management Uygulaması

## Genel Bakış
Car Management uygulaması iki modülden oluşur: Frontend için Angular ve Backend için Spring Boot kullanılmıştır. Uygulama, araç ve marka için CRUD işlemlerini yapabilmenizi, filtreleme ve sıralama işlemlerini gerçekleştirebilmenizi sağlar.

## Başlangıç

### Gereksinimler
- Java JDK 17+
- Node.js ve npm
- Angular CLI
- Maven
- PostgreSQL

### Kurulum ve Çalıştırma

#### Backend Modülü
Backend projesini başlatmak için aşağıdaki adımları izleyin:
```bash
cd BackendModule
mvn spring-boot:run
```
Backend projesi varsayılan olarak [http://localhost:10150](http://localhost:10150) portunda çalışır.

#### Frontend Modülü
Frontend projesini başlatmak için aşağıdaki adımları izleyin:
```bash
cd ../FrontendModule
ng serve
```
Frontend projesi varsayılan olarak [http://localhost:4150](http://localhost:4150) portunda çalışır.

## Port Değişikliği

### Backend Port Değişikliği
Backend projesinde portu değiştirmek için `application.properties` dosyasındaki `server.port` değerini düzenleyin:
```properties
server.port=10150
```

### Frontend Port Değişikliği
Frontend projesinde portu değiştirmek için `angular.json` dosyasındaki aşağıdaki kısmı düzenleyin:
```json
{
  ...
  "projects": {
    "your-project-name": {
      "architect": {
        "serve": {
          "options": {
            "port": 4150
          }
        }
      }
    }
  }
  ...
}
```

## Swagger UI
API dokümantasyonuna erişmek için backend modülü çalıştıktan sonra aşağıdaki URL'yi ziyaret edebilirsiniz:
```
http://localhost:10150/swagger-ui/index.html
```

## Kullanılan Teknolojiler
### Frontend:
- Angular
- TypeScript
- HTML / CSS
- Bootstrap

### Backend:
- Spring Boot
- Java
- Maven
- JPA / Hibernate
- Camunda

### Veritabanı:
- PostgreSQL
- Redis
