CREATE TABLE IF NOT EXISTS `streamer` (
`id` bigint NOT NULL,
`uuid` varchar(36) NOT NULL,
`birth_date` date NOT NULL,
`first_name` varchar(50) NOT NULL,
`last_name` varchar(50) NOT NULL,
`pseudo` varchar(50) NOT NULL,
`channel` varchar(100) NOT NULL,
`rule` int NOT NULL,
`status` int NOT NULL,
`auth_data_id` bigint DEFAULT NULL,
`created_at` datetime(6) DEFAULT NULL,
`updated_at` datetime(6) DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `UKgx8qgn1tp5aedpoami0ilv02g` (`uuid`),
UNIQUE KEY `UKg9wso2ihi5bm2y1kup7c250d5` (`pseudo`),
UNIQUE KEY `UKia2nttx1btykhg9vmn1r115pj` (`auth_data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `authentication_data` (
`id` bigint NOT NULL,
`email` varchar(100) NOT NULL,
`password` varchar(30) NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `UKhlvtcx7w5shxtwmpohrippwcn` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `live` (
`id` bigint NOT NULL,
`uuid` varchar(36) NOT NULL,
`title` varchar(100) NOT NULL,
`description` varchar(255) DEFAULT NULL,
`themes` varchar(255) NOT NULL,
`pegi` int NOT NULL,
`date_end` datetime(6) NOT NULL,
`date_start` datetime(6) NOT NULL,
`created_at` datetime(6) DEFAULT NULL,
`updated_at` datetime(6) DEFAULT NULL,
`streamer_id` bigint NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `UKbc3x9bfgu33dssub3eqvv4eh9` (`uuid`),
KEY `FKt1y90s6o73739607ri4jty9vc` (`streamer_id`),
CONSTRAINT `FKt1y90s6o73739607ri4jty9vc` FOREIGN KEY (`streamer_id`) REFERENCES `streamer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `equipment` (
`quantity` int NOT NULL,
`created_at` datetime(6) DEFAULT NULL,
`id` bigint NOT NULL,
`updated_at` datetime(6) DEFAULT NULL,
`brand` varchar(255) NOT NULL,
`label` varchar(255) NOT NULL,
`uuid` varchar(255) NOT NULL,
`equipment_type` enum('ACCESORIES','CAMERA','CAPTURE_CARD','COMPUTER','FURNITURE','HEADPHONES','INTERNET_CONNECTION','LIGHTING','MICROPHONE','MONITOR','SOFTWARE') DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `UK7emrr367d9h47cv4eg8c3jb68` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `user` (
`id` bigint NOT NULL,
`live_id` bigint DEFAULT NULL,
`uuid` varchar(36) NOT NULL,
`email` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `UK1xc1iry6gqjrvh5cpajiq7l2f` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;