create database shopapp;
use shopapp;

create table roles(
id int primary key,
name varchar(10) not null
);

create table users(
	id int primary key auto_increment,
    fullname varchar(50) not null,
    phone_number varchar(12) not null,
    address varchar(200) default null,
    password varchar(100) not null default '',
    created_at datetime ,
    updated_at datetime,
    is_active tinyint(1) default 1,
    date_of_birth date,
    facebook_account_id int default 0,
    google_account_id int default 0,
    role_id int,
    foreign key (role_id) references roles(id)
);


create table tokens(
    id int primary key auto_increment,
    token varchar(255) unique not null,
    token_type varchar(50) not null,
    expiration_date datetime,
    revoked tinyint(1) not null,
    expired tinyint(1) not null,
    user_id int,
    foreign key (user_id) references users(id)
);
create table social_accounts(
    id int primary key auto_increment,
    provider varchar(20) not null comment 'Tên nhà social network',
    provider_id varchar(50) not null,
    email varchar(150) not null comment 'Email tài khoản',
    name varchar(100) not null comment 'Tên người dùng',
    user_id int,
    foreign key (user_id) references users(id)
);

create table categories(
    id int primary key auto_increment,
    name varchar(100) not null default '' comment 'Tên danh mục, vd: đồ điện lạnh'
);

create table products (
    id int primary key auto_increment,
    name varchar(350) comment 'Tên sản phẩm',
    price float not null check (price >= 0),
    thumbnail varchar(300) default '',
    description longtext default null comment 'Mô tả sản phẩm',
    created_at datetime,
    updated_at datetime,
    category_id int,
    foreign key (category_id) references categories (id)
);

create table products_images(
	id int primary key auto_increment,
    product_id int,
    constraint fk_product_images_product_id
    foreign key (product_id) references products(id) on delete cascade,
    image_url  varchar(300)
);

create table orders(
    id int primary key auto_increment,
    user_id int,
    foreign key (user_id) references users(id),
    fullname varchar(100) default null,
    email varchar(100) default null,
    phone_number varchar(20) not null,
    address varchar(200) not null,
    note varchar(100) default '',
    order_date datetime default current_timestamp,
    total_money float check(total_money >= 0),
    shipping_method varchar(100),
    shipping_address varchar(200),
    shipping_date date,
    tracking_code varchar(100),
    payment_method varchar(100),
    is_active tinyint(1),
	status enum('pending', 'processing', 'shipped', 'delivered', 'cancelled') comment 'Trạng thái đơn hàng'
);

create table order_details(
	id int primary key auto_increment,
    order_id int,
    foreign key (order_id) references orders(id),
    product_id int,
    foreign key (product_id) references products(id),
    price float  check (price>=0),
    number_of_products int check(number_of_products > 0),
    total_money float check(total_money > 0),
    color varchar(20) default null
);
