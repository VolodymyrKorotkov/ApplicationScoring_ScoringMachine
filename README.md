# ApplicationScoring_ScoringMachine


This is my first Java application, which I once wrote for myself and decided to share with the world, perhaps it will become useful to someone. The essence of the application: this is a web application with the possibility of registration / authorization. In the application itself, it is possible to create scoring models, credit, application scoring. To create a scoring, you need to prepare the data and upload the xlsx file to the system (model building can be controlled by settings), then you will receive an email notification after the model creation or its test.

Spring, Java, JSP, PostgreSQL, Hibernate were used.

To use the application, you need:

- in the public interface PortalConstants, you can change the time zone, the home url, which is used to generate links to email templates.

- in the public abstract class EmailConfig, it is desirable to create several of your mailboxes in Google, allow SMTP connections there and enter a new login and password here. While sending messages, the service simply sorts through mailboxes one by one so that Google does not block them as spam.

- in db.properties replace connections to your database, local or cloud. The database was used by PostgreSQL.

- in the interface ApiFondyConstants, you can register real credits for working with the Fondy payment service (integration is ready there) and then just include the commented-out code in the SubscriptionsController controller and comment out what is below "//without integration with Fondy". This is just a stub to make the application work for "purchases" of different subscriptions and accesses without integration with the payment service.

- create a database with a number of tables (scripts are written for the admin user, if necessary, you can replace):

create sequence payment_requests_id_seq;

alter sequence payment_requests_id_seq owner to admin;

create table user_role
(
    id                              bigserial
        constraint user_role_pk
            primary key,
    description                     text,
    name                            varchar not null,
    count_max_saved_models          integer not null,
    count_max_saved_tests_for_model integer,
    price_one_month                 integer,
    price_three_months              integer,
    price_six_months                integer,
    price_one_year                  integer
);

alter table user_role
    owner to admin;

create table user_account
(
    id                          bigserial
        constraint user_account_pk
            primary key,
    created_at                  timestamp,
    last_modified_at            timestamp,
    email                       varchar               not null,
    first_name                  varchar,
    last_name                   varchar,
    locked_until                timestamp,
    login                       varchar               not null,
    password                    varchar               not null,
    password_change_required    boolean default false,
    status                      varchar               not null,
    super_user                  boolean,
    role_id                     bigint
        constraint user_account_user_role_id_fk
            references user_role
            on update cascade,
    email_confirmed             boolean default false not null,
    password_expired_at         timestamp,
    temp_password_during_change varchar,
    dynamic_code                varchar,
    date_of_birth               date,
    gender                      varchar,
    last_payment_at             timestamp,
    subscription_expired_at     timestamp,
    temp_email_during_change    varchar
);

alter table user_account
    owner to admin;

create unique index user_account_id_uindex
    on user_account (id);

create unique index user_account_login_uindex
    on user_account (login);

create unique index user_account_password_uindex
    on user_account (password);

create unique index user_role_id_uindex
    on user_role (id);

create unique index user_role_name_uindex
    on user_role (name);

create table scoring_model
(
    id               bigserial
        constraint scoring_model_pk
            primary key,
    created_at       timestamp,
    last_modified_at timestamp,
    client_id        bigint not null
        constraint scoring_model_user_account_id_fk
            references user_account
            on update cascade,
    title            varchar,
    description      text,
    status           varchar
);

alter table scoring_model
    owner to admin;

create unique index scoring_model_id_uindex
    on scoring_model (id);

create table scoring_model_parameters
(
    id                       bigserial
        constraint scoring_model_parameters_pk
            primary key,
    created_at               timestamp,
    last_modified_at         timestamp,
    model_id                 bigint not null
        constraint scoring_model_parameters_scoring_model_id_fk
            references scoring_model
            on update cascade on delete cascade,
    title                    varchar,
    name_parameter           varchar,
    good_count               integer,
    bad_count                integer,
    good_rate                numeric,
    bad_rate                 numeric,
    total_count              integer,
    good_population_percent  numeric,
    bad_population_percent   numeric,
    total_population_percent numeric,
    gi_g                     numeric,
    bi_b                     numeric,
    pg_pb                    numeric,
    woe                      numeric,
    iv                       numeric,
    score                    integer,
    type_parameter           varchar,
    recommended              boolean,
    total                    boolean
);

alter table scoring_model_parameters
    owner to admin;

create unique index scoring_model_parameters_id_uindex
    on scoring_model_parameters (id);

create table scoring_settings
(
    id                                                 bigserial
        constraint scoring_settings_pk
            primary key,
    created_at                                         timestamp,
    last_modified_at                                   timestamp,
    client_id                                          bigint
        constraint scoring_settings_user_account_id_fk
            references user_account
            on update cascade,
    good_result                                        varchar,
    bad_result                                         varchar,
    minimum_needed_iv_for_parameter_one                numeric,
    minimum_needed_average_iv_for_key_of_parameter_one numeric,
    minimum_needed_iv_for_parameter_two                numeric,
    minimum_needed_average_iv_for_key_of_parameter_two numeric,
    max_rows_for_influence_parameter_two               integer,
    factor                                             integer,
    off_set                                            integer,
    number_wished_rows_for_calc_test_model             integer,
    model_quality_level                                varchar
);

alter table scoring_settings
    owner to admin;

create table test_scoring_model
(
    id               bigserial
        constraint test_scoring_model_pk
            primary key,
    created_at       timestamp not null,
    last_modified_at timestamp not null,
    client_id        bigint    not null
        constraint test_scoring_model_user_account_id_fk
            references user_account
            on update cascade,
    scoring_model_id bigint    not null
        constraint test_scoring_model_scoring_model_id_fk
            references scoring_model
            on update cascade on delete cascade,
    title            varchar   not null,
    description      text,
    gini_index       numeric
);

alter table test_scoring_model
    owner to admin;

create unique index test_scoring_model_id_uindex
    on test_scoring_model (id);

create table test_scoring_model_results
(
    id                      bigserial
        constraint test_scoring_model_results_pk
            primary key,
    created_at              timestamp not null,
    last_modified_at        timestamp not null,
    test_scoring_model_id   bigint    not null
        constraint test_scoring_model_results_test_scoring_model_id_fk
            references test_scoring_model
            on update cascade on delete cascade,
    score                   varchar,
    count_total_items       integer,
    count_good_items        integer,
    count_bad_items         integer,
    bad_rate                numeric,
    cum_total_items_count   integer,
    cum_total_items_percent numeric,
    cum_good_items_count    integer,
    cum_good_items_percent  numeric,
    cum_bad_items_count     integer,
    cum_bad_items_percent   numeric,
    gini_bi_bi_one          numeric,
    gini_gi_gi_one          numeric,
    gini_result             numeric,
    order_number_row        integer,
    total                   boolean
);

alter table test_scoring_model_results
    owner to admin;

create unique index test_scoring_model_results_id_uindex
    on test_scoring_model_results (id);

create table payments
(
    id                    bigint default nextval('payment_requests_id_seq'::regclass) not null
        constraint payment_requests_pk
            primary key,
    created_at            timestamp,
    last_modified_at      timestamp,
    client_id             bigint
        constraint payment_requests_user_account_id_fk
            references user_account
            on update cascade,
    purchase_role_id      bigint
        constraint payment_requests_user_role_id_fk
            references user_role,
    status_response       varchar,
    merchant_id           integer,
    order_desc            varchar,
    amount                numeric,
    currency              varchar,
    response_url          varchar,
    server_callback_url   varchar,
    sender_email_request  varchar,
    order_status          varchar,
    tran_type             varchar,
    sender_cell_phone     varchar,
    sender_account        varchar,
    masked_card           varchar,
    card_bin              integer,
    card_type             varchar,
    rrn                   varchar,
    approval_code         varchar,
    response_code         integer,
    response_description  varchar,
    reversal_amount       numeric,
    settlement_amount     numeric,
    settlement_currency   varchar,
    order_time            timestamp,
    settlement_date       date,
    eci                   integer,
    fee                   numeric,
    payment_system        varchar,
    sender_email_response varchar,
    payment_id            integer,
    actual_amount         numeric,
    actual_currency       varchar,
    rectoken              varchar,
    rectoken_lifetime     timestamp,
    temp_response_status  varchar,
    temp_checkout_url     varchar,
    temp_payment_id       integer,
    error_code            integer,
    error_message         varchar,
    period_purchase       varchar,
    type_purchase         varchar,
    status_request        varchar,
    error_request         varchar
);

alter table payments
    owner to admin;

alter sequence payment_requests_id_seq owned by payments.id;

create unique index payment_requests_id_uindex
    on payments (id);
    
    
__________________________________________________________________________________________________________________________________________________________



Это мое первое приложение на Java, которая я когда-то писал для себя и решил поделиться с миром, возможно, кому-то станет полезным.
Суть приложения: это web-приложение с возможностью регистрации/авторизации. В самом приложении возможно создавать скоринговые модели, кредитный, апликационный скоринг.
Для создания скоринга вам необходимо подготовить данные и загрузить файл xlsx в систему (построение модели можно регулировать настройками), дальше по итогу создания модели или ее теста вы получите имейл-уведомление.

Использовались Spring, Java, JSP, PostgreSQL, Hibernate.

Для использования приложения, нужно:

- в public interface PortalConstants можно поменять тайм зону, урл домашний, который используется для генерации ссылок в имейл-темплейты.

- в public abstract class EmailConfig желательно создать несколько своих почтовых ящиков в гугле, разрешить там SMTP-подключения и прописать здесь новые логин и пароль. Во время отправки сообщений сервис просто перебирает поочереди почтовые ящики, чтобы гугл их не блокировал как спам.

- в db.properties заменить подключения к базе данных вашей, локальной или облачной. БД использовалась PostgreSQL.

- в interface ApiFondyConstants можно прописать реальные креды для работы с платежным сервисом Fondy (там готова интеграция) и тогда просто включить закомментированный код в контроллере SubscriptionsController и закомментировать в замен то, что ниже "//without integration with Fondy". Это просто заглушка, чтобы приложение работало для "покупок" разных подписок и доступов без интеграции с платежным сервисом.

- создать БД с рядом таблиц (скрипты написаны для пользователя admin, если нужно, можете заменить):

create sequence payment_requests_id_seq;

alter sequence payment_requests_id_seq owner to admin;

create table user_role
(
    id                              bigserial
        constraint user_role_pk
            primary key,
    description                     text,
    name                            varchar not null,
    count_max_saved_models          integer not null,
    count_max_saved_tests_for_model integer,
    price_one_month                 integer,
    price_three_months              integer,
    price_six_months                integer,
    price_one_year                  integer
);

alter table user_role
    owner to admin;

create table user_account
(
    id                          bigserial
        constraint user_account_pk
            primary key,
    created_at                  timestamp,
    last_modified_at            timestamp,
    email                       varchar               not null,
    first_name                  varchar,
    last_name                   varchar,
    locked_until                timestamp,
    login                       varchar               not null,
    password                    varchar               not null,
    password_change_required    boolean default false,
    status                      varchar               not null,
    super_user                  boolean,
    role_id                     bigint
        constraint user_account_user_role_id_fk
            references user_role
            on update cascade,
    email_confirmed             boolean default false not null,
    password_expired_at         timestamp,
    temp_password_during_change varchar,
    dynamic_code                varchar,
    date_of_birth               date,
    gender                      varchar,
    last_payment_at             timestamp,
    subscription_expired_at     timestamp,
    temp_email_during_change    varchar
);

alter table user_account
    owner to admin;

create unique index user_account_id_uindex
    on user_account (id);

create unique index user_account_login_uindex
    on user_account (login);

create unique index user_account_password_uindex
    on user_account (password);

create unique index user_role_id_uindex
    on user_role (id);

create unique index user_role_name_uindex
    on user_role (name);

create table scoring_model
(
    id               bigserial
        constraint scoring_model_pk
            primary key,
    created_at       timestamp,
    last_modified_at timestamp,
    client_id        bigint not null
        constraint scoring_model_user_account_id_fk
            references user_account
            on update cascade,
    title            varchar,
    description      text,
    status           varchar
);

alter table scoring_model
    owner to admin;

create unique index scoring_model_id_uindex
    on scoring_model (id);

create table scoring_model_parameters
(
    id                       bigserial
        constraint scoring_model_parameters_pk
            primary key,
    created_at               timestamp,
    last_modified_at         timestamp,
    model_id                 bigint not null
        constraint scoring_model_parameters_scoring_model_id_fk
            references scoring_model
            on update cascade on delete cascade,
    title                    varchar,
    name_parameter           varchar,
    good_count               integer,
    bad_count                integer,
    good_rate                numeric,
    bad_rate                 numeric,
    total_count              integer,
    good_population_percent  numeric,
    bad_population_percent   numeric,
    total_population_percent numeric,
    gi_g                     numeric,
    bi_b                     numeric,
    pg_pb                    numeric,
    woe                      numeric,
    iv                       numeric,
    score                    integer,
    type_parameter           varchar,
    recommended              boolean,
    total                    boolean
);

alter table scoring_model_parameters
    owner to admin;

create unique index scoring_model_parameters_id_uindex
    on scoring_model_parameters (id);

create table scoring_settings
(
    id                                                 bigserial
        constraint scoring_settings_pk
            primary key,
    created_at                                         timestamp,
    last_modified_at                                   timestamp,
    client_id                                          bigint
        constraint scoring_settings_user_account_id_fk
            references user_account
            on update cascade,
    good_result                                        varchar,
    bad_result                                         varchar,
    minimum_needed_iv_for_parameter_one                numeric,
    minimum_needed_average_iv_for_key_of_parameter_one numeric,
    minimum_needed_iv_for_parameter_two                numeric,
    minimum_needed_average_iv_for_key_of_parameter_two numeric,
    max_rows_for_influence_parameter_two               integer,
    factor                                             integer,
    off_set                                            integer,
    number_wished_rows_for_calc_test_model             integer,
    model_quality_level                                varchar
);

alter table scoring_settings
    owner to admin;

create table test_scoring_model
(
    id               bigserial
        constraint test_scoring_model_pk
            primary key,
    created_at       timestamp not null,
    last_modified_at timestamp not null,
    client_id        bigint    not null
        constraint test_scoring_model_user_account_id_fk
            references user_account
            on update cascade,
    scoring_model_id bigint    not null
        constraint test_scoring_model_scoring_model_id_fk
            references scoring_model
            on update cascade on delete cascade,
    title            varchar   not null,
    description      text,
    gini_index       numeric
);

alter table test_scoring_model
    owner to admin;

create unique index test_scoring_model_id_uindex
    on test_scoring_model (id);

create table test_scoring_model_results
(
    id                      bigserial
        constraint test_scoring_model_results_pk
            primary key,
    created_at              timestamp not null,
    last_modified_at        timestamp not null,
    test_scoring_model_id   bigint    not null
        constraint test_scoring_model_results_test_scoring_model_id_fk
            references test_scoring_model
            on update cascade on delete cascade,
    score                   varchar,
    count_total_items       integer,
    count_good_items        integer,
    count_bad_items         integer,
    bad_rate                numeric,
    cum_total_items_count   integer,
    cum_total_items_percent numeric,
    cum_good_items_count    integer,
    cum_good_items_percent  numeric,
    cum_bad_items_count     integer,
    cum_bad_items_percent   numeric,
    gini_bi_bi_one          numeric,
    gini_gi_gi_one          numeric,
    gini_result             numeric,
    order_number_row        integer,
    total                   boolean
);

alter table test_scoring_model_results
    owner to admin;

create unique index test_scoring_model_results_id_uindex
    on test_scoring_model_results (id);

create table payments
(
    id                    bigint default nextval('payment_requests_id_seq'::regclass) not null
        constraint payment_requests_pk
            primary key,
    created_at            timestamp,
    last_modified_at      timestamp,
    client_id             bigint
        constraint payment_requests_user_account_id_fk
            references user_account
            on update cascade,
    purchase_role_id      bigint
        constraint payment_requests_user_role_id_fk
            references user_role,
    status_response       varchar,
    merchant_id           integer,
    order_desc            varchar,
    amount                numeric,
    currency              varchar,
    response_url          varchar,
    server_callback_url   varchar,
    sender_email_request  varchar,
    order_status          varchar,
    tran_type             varchar,
    sender_cell_phone     varchar,
    sender_account        varchar,
    masked_card           varchar,
    card_bin              integer,
    card_type             varchar,
    rrn                   varchar,
    approval_code         varchar,
    response_code         integer,
    response_description  varchar,
    reversal_amount       numeric,
    settlement_amount     numeric,
    settlement_currency   varchar,
    order_time            timestamp,
    settlement_date       date,
    eci                   integer,
    fee                   numeric,
    payment_system        varchar,
    sender_email_response varchar,
    payment_id            integer,
    actual_amount         numeric,
    actual_currency       varchar,
    rectoken              varchar,
    rectoken_lifetime     timestamp,
    temp_response_status  varchar,
    temp_checkout_url     varchar,
    temp_payment_id       integer,
    error_code            integer,
    error_message         varchar,
    period_purchase       varchar,
    type_purchase         varchar,
    status_request        varchar,
    error_request         varchar
);

alter table payments
    owner to admin;

alter sequence payment_requests_id_seq owned by payments.id;

create unique index payment_requests_id_uindex
    on payments (id);

