# 📖 Таблица Nginx директив (контекст + значения + regex + тип)

| Директива | Контексты | Допустимые значения | Regex / Patterns | Тип | Описание |
|-----------|-----------|----------------------|------------------|-----|----------|
| **user** | `main` | имя_пользователя [группа] | ✖ | Single | Пользователь/группа, под которыми работают воркеры |
| **worker_processes** | `main` | число \| `auto` | ✖ | Single | Количество процессов-воркеров |
| **error_log** | `main`, `http`, `mail`, `stream` | путь [уровень] | ✖ | Single | Лог ошибок |
| **pid** | `main` | путь | ✖ | Single | PID-файл процесса |
| **events { }** | `main` | блок | ✖ | Block | Настройки обработки соединений |
| **worker_connections** | `events` | число | ✖ | Single | Макс. число соединений на воркер |
| **multi_accept** | `events` | `on` \| `off` | ✖ | Single | Принимать несколько соединений за один цикл |
| **http { }** | `main` | блок | ✖ | Block | Контейнер HTTP-настроек |
| **server { }** | `http`, `stream`, `mail` | блок | ✖ | Block | Виртуальный сервер |
| **listen** | `server` | порт \| IP:порт \| unix:сокет | ✖ | Single | Адреса/порты, на которых слушает сервер |
| **server_name** | `server` (http) | список строк \| `_` | ✅ (wildcard, regex) | Single | Имена хостов |
| **location { }** | `server` | URI или шаблон | ✅ | Block | Обработка запросов по пути |
| **root** | `http`, `server`, `location`, `if` | путь | ✖ | Single | Корневой каталог для файлов |
| **alias** | `location` | путь | ✖ | Single | Альтернативный путь |
| **index** | `http`, `server`, `location` | список файлов | ✖ | Single | Главный файл по умолчанию |
| **include** | `main`, `http`, `server`, `location`, `mail`, `stream` | путь (может быть `*`) | ✅ (glob) | Single | Подключение файлов |
| **access_log** | `http`, `server`, `location` | путь \| `off` [формат] | ✖ | Single | Лог запросов |
| **log_format** | `http` | имя + строка формата | ✖ | Single | Формат access-логов |
| **sendfile** | `http`, `server`, `location` | `on` \| `off` | ✖ | Single | Использовать sendfile() |
| **keepalive_timeout** | `http`, `server`, `location` | время | ✖ | Single | Таймаут keep-alive соединений |
| **gzip** | `http`, `server`, `location` | `on` \| `off` | ✖ | Single | Включить gzip-сжатие |
| **gzip_types** | `http`, `server`, `location` | список MIME-типов | ✖ | Single | MIME-типы для gzip |
| **proxy_pass** | `location`, `if` | URL | ✖ | Single | Проксирование запросов |
| **proxy_set_header** | `http`, `server`, `location` | имя значение | ✖ | Single | Заголовки при проксировании |
| **proxy_redirect** | `http`, `server`, `location` | `default` \| `off` \| regex | ✅ | Single | Перезапись `Location` |
| **return** | `server`, `location`, `if` | код \| URL | ✖ | Single | Немедленный возврат ответа |
| **rewrite** | `server`, `location`, `if` | regex replacement [флаг] | ✅ | Single | Переписывание URI |
| **upstream { }** | `http`, `stream` | список `server` | ✖ | Block | Балансировка нагрузки |
| **limit_req_zone** | `http` | ключ zone=имя:размер rate=число | ✖ | Single | Ограничение числа запросов |
| **limit_conn_zone** | `http` | ключ zone=имя:размер | ✖ | Single | Ограничение соединений |
| **deny** | `http`, `server`, `location` | `all` \| IP \| CIDR | ✖ | Single | Запрет доступа |
| **allow** | `http`, `server`, `location` | `all` \| IP \| CIDR | ✖ | Single | Разрешение доступа |
| **ssl_certificate** | `http`, `server` | путь | ✖ | Single | SSL/TLS сертификат |
| **ssl_certificate_key** | `http`, `server` | путь | ✖ | Single | Закрытый ключ |
| **ssl_protocols** | `http`, `server` | список (`TLSv1.2`, `TLSv1.3`) | ✖ | Single | Разрешённые протоколы |
| **ssl_ciphers** | `http`, `server` | строка (OpenSSL ciphers) | ✖ | Single | Набор шифров |
| **stream { }** | `main` | блок | ✖ | Block | TCP/UDP проксирование |
| **mail { }** | `main` | блок | ✖ | Block | Почтовый прокси |
| **proxy_pass (stream)** | `server` (stream) | адрес/бэкенд | ✖ | Single | TCP/UDP прокси |
| **proxy_timeout** | `stream`, `server` (stream) | время | ✖ | Single | Таймаут прокси-сессии |
| **proxy_connect_timeout** | `stream`, `server` (stream) | время | ✖ | Single | Таймаут установления соединения |
