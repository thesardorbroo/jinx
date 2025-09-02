
# 📘 Nginx Directives Reference

Полный справочник основных директив Nginx с указанием:

- **Context (Контекст)** — где директива может использоваться.
- **Block / Non-block** — блочная или одиночная директива.
- **Possible Values (Допустимые значения)** — какие значения принимает.
- **Regex/Pattern Support (Поддержка паттернов)** — поддерживает ли regex или шаблоны.

---

| Directive        | Contexts                        | Block / Non-block | Possible Values / Examples                                                                 | Regex / Pattern Support | Description |
|------------------|---------------------------------|-------------------|---------------------------------------------------------------------------------------------|--------------------------|-------------|
| `user`           | main                            | Non-block         | имя пользователя, ID (e.g., `nginx`, `www-data`)                                           | ❌                        | Указывает пользователя для рабочих процессов |
| `worker_processes` | main                          | Non-block         | число или `auto`                                                                            | ❌                        | Количество воркеров |
| `error_log`      | main, http, server, location    | Non-block         | путь + уровень (`/var/log/nginx/error.log warn`)                                            | ❌                        | Лог ошибок |
| `pid`            | main                            | Non-block         | путь (например `/var/run/nginx.pid`)                                                        | ❌                        | Файл PID |
| `events`         | main                            | Block             | содержит директивы (`worker_connections`, `multi_accept`)                                   | ❌                        | Настройки событий |
| `worker_connections` | events                      | Non-block         | число                                                                                       | ❌                        | Количество соединений на воркер |
| `http`           | main                            | Block             | содержит `server`, `location`, proxy, rewrite и т.д.                                        | ❌                        | HTTP-блок |
| `server`         | http                            | Block             | содержит `location`, `listen`, ssl, proxy, rewrite и т.д.                                   | ❌                        | Виртуальный хост |
| `listen`         | server                          | Non-block         | порт, IP, параметры (`80`, `443 ssl`, `[::]:80`)                                            | ❌                        | Указывает порт/адрес |
| `server_name`    | server                          | Non-block         | хостнеймы или шаблоны (`example.com`, `*.example.org`, `~^.*\.example\.org$`)             | ✅                        | Имя хоста (поддержка regex и wildcard) |
| `location`       | server                          | Block             | путь или regex (`/images/`, `~ \.php$`, `=` точное совпадение)                             | ✅                        | Маршрутизация запросов |
| `root`           | http, server, location, if      | Non-block         | путь (`/var/www/html`)                                                                      | ❌                        | Корневая директория |
| `index`          | http, server, location          | Non-block         | список файлов (`index.html index.htm`)                                                      | ❌                        | Индексный файл |
| `proxy_pass`     | location, if (внутри location)  | Non-block         | URL (`http://127.0.0.1:8080`, `http://backend`)                                             | ❌                        | Проксирование |
| `proxy_set_header` | http, server, location        | Non-block         | `Header value` (`Host $host`, `X-Real-IP $remote_addr`)                                     | ❌                        | Установка заголовков |
| `proxy_redirect` | http, server, location          | Non-block         | `default`, `off`, или выражение для подмены                                                | ❌                        | Переписывание redirect |
| `proxy_cache`    | http, server, location          | Non-block         | имя кэша                                                                                    | ❌                        | Включает кэширование |
| `proxy_cache_path` | main, http                    | Block             | путь и параметры (`/data/nginx/cache levels=1:2 keys_zone=one:10m`)                         | ❌                        | Конфиг кэша |
| `rewrite`        | server, location, if            | Non-block         | regex + замена + [флаги] (`rewrite ^/old$ /new permanent;`)                                 | ✅                        | Переписывание URL |
| `return`         | server, location, if            | Non-block         | код/URL (`return 301 https://example.com;`, `return 200 'OK';`)                             | ❌                        | Немедленный ответ |
| `try_files`      | server, location                | Non-block         | список файлов + fallback (`try_files $uri /index.html =404;`)                               | ❌                        | Проверка файлов |
| `ssl_certificate` | http, server                   | Non-block         | путь к сертификату                                                                          | ❌                        | SSL-сертификат |
| `ssl_certificate_key` | http, server               | Non-block         | путь к приватному ключу                                                                     | ❌                        | SSL-ключ |
| `ssl_protocols`  | http, server                    | Non-block         | список протоколов (`TLSv1 TLSv1.2 TLSv1.3`)                                                 | ❌                        | Разрешённые SSL/TLS протоколы |
| `ssl_ciphers`    | http, server                    | Non-block         | список шифров                                                                               | ❌                        | Контроль шифров |
| `include`        | любой                           | Non-block         | путь или шаблон (`/etc/nginx/conf.d/*.conf`)                                                | ✅ (glob patterns)        | Подключение файлов |
| `access_log`     | http, server, location, if      | Non-block         | путь + формат (`/var/log/nginx/access.log combined`)                                        | ❌                        | Лог доступа |
| `log_format`     | http                            | Block             | имя + шаблон (`log_format main '$remote_addr - $request';`)                                 | ✅ (vars)                 | Определяет формат логов |
| `gzip`           | http, server, location          | Non-block         | `on` / `off`                                                                                | ❌                        | Включает Gzip |
| `gzip_types`     | http, server, location          | Non-block         | список MIME типов (`text/plain text/css application/json`)                                  | ❌                        | MIME-типы для Gzip |
| `upstream`       | http                            | Block             | список серверов (`server backend1.example.com;`)                                            | ❌                        | Определяет backend pool |
| `server` (в upstream) | upstream                   | Non-block         | адрес + параметры (`server 127.0.0.1:8080 max_fails=3;`)                                    | ❌                        | Список апстрим серверов |

---

⚠️ Это не полный список всех директив Nginx, но он покрывает **80% самых часто используемых**.

