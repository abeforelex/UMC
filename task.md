## Что добавлено:
- **Event функциональность**: созданы EventDomainService, EventSot, EventSave, EventPgDao, EventPgEntity, EventPgMapper, EventPgRepository
- **GraphQL API**: добавлены EventDataLoader, EventDgsMutation, EventDgsQuery, схемы event-mutation.graphqls и event-query.graphqls
- **Venue доработки**: обновлены VenueDomainService, VenueSot, VenuePgDao, VenuePgRepository
- **База данных**: добавлена миграция V2__event_tables_v1.sql, переименована V1__venue_table_v1.sql
- **Unit тесты**: создан VenueDomainServiceUnitTest.java с 5 тестами
- **Конфигурация**: обновлены graphql.yaml, application.yaml, build.gradle, gradle.properties
- **Скаляры**: добавлен LocalDateTimeScalar для GraphQL

## Результат:
- Полная реализация Event функциональности (CRUD операции)
- Расширенная Venue функциональность
- GraphQL API для работы с событиями и площадками
- Покрытие тестами базовой функциональности
