create table customer
(
    customer_id      serial,
    surname          varchar(20),
    givenname        varchar(20),
    dob              DATE,
    gender           varchar(20),
    customer_profile varchar(20),
    primary key (customer_id)
);


create table orderdetail
(
    order_id            serial,
    customer_id         integer,
    travel_id integer,
    order_received_date TIMESTAMP,
    order_actioned_date TIMESTAMP,
    order_status        varchar(20),
    primary key (order_id)
);


create table traveldetail
(
    travel_id   serial,
    start_date  TIMESTAMP,
    end_date    TIMESTAMP,
    source      varchar(20),
    destination varchar(20),
    travel_type varchar(20),
    primary key (travel_id)
);
