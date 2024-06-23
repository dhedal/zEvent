CREATE TABLE IF NOT EXISTS `streamer` (
`id` bigint NOT NULL,
`age` int NOT NULL,
`chaine` varchar(100) NOT NULL,
`created_at` datetime(6) DEFAULT NULL,
`email` varchar(100) NOT NULL,
`first_name` varchar(50) NOT NULL,
`last_name` varchar(50) NOT NULL,
`matricule` varchar(255) NOT NULL,
`rule` int NOT NULL,
`updated_at` datetime(6) DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `UK2shs6h3sf08t0vldshliuwvjw` (`email`),
UNIQUE KEY `UKaoysjgkrh6vp74oa6jhendvn7` (`matricule`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `live` (
`id` bigint NOT NULL,
`created_at` datetime(6) DEFAULT NULL,
`date_end` datetime(6) NOT NULL,
`date_start` datetime(6) NOT NULL,
`label` varchar(100) NOT NULL,
`pegi` int NOT NULL,
`theme` tinyint NOT NULL,
`updated_at` datetime(6) DEFAULT NULL,
`streamer_id` bigint NOT NULL,
PRIMARY KEY (`id`),
KEY `FKt1y90s6o73739607ri4jty9vc` (`streamer_id`),
CONSTRAINT `FKt1y90s6o73739607ri4jty9vc` FOREIGN KEY (`streamer_id`) REFERENCES `streamer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `equipment` (
`id` bigint NOT NULL,
`brand` varchar(255) NOT NULL,
`created_at` datetime(6) DEFAULT NULL,
`equipment_type` enum('ACCESORIES','CAMERA','CAPTURE_CARD','COMPUTER','FURNITURE','HEADPHONES','INTERNET_CONNECTION','LIGHTING','MICROPHONE','MONITOR','SOFTWARE') DEFAULT NULL,
`label` varchar(255) NOT NULL,
`quantity` int NOT NULL,
`updated_at` datetime(6) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `user` (
`id` bigint NOT NULL,
`email` varchar(255) DEFAULT NULL,
`live_id` bigint DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;