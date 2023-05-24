SET MODE MYSQL;

CREATE TABLE IF NOT EXISTS `prices` (
	`price_list_id` long int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `brand_id` int NOT NULL,
    `end_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `priority` int,
    `price` decimal(9,2) NOT NULL,
    `currency_code` VARCHAR(3)
    `product_id` int NOT NULL,
    `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    )ENGINE=InnoDB DEFAULT CHARSET=UTF8 AUTO_INCREMENT=1;

