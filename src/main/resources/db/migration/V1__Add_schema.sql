create table product
(
    id          int auto_increment primary key,
    name        varchar(255) not null,
    description varchar(100) not null
);

create table review
(
    id          int auto_increment primary key,
    product_id  int           not null,
    description varchar(1000) not null,
    foreign key (product_id) references product (id)
);

create table comment
(
    id          int auto_increment primary key,
    review_id   int           not null,
    description varchar(1000) not null,
    foreign key (review_id) references review (id)
);