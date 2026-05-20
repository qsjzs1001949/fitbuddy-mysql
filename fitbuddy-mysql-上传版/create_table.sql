CREATE TABLE IF NOT EXISTS `partner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `fitness_type` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) DEFAULT 'user' COMMENT '角色：admin或user',
  `status` varchar(20) DEFAULT 'active' COMMENT '状态：active(正常)、muted(禁言)、banned(封禁)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_unique` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `partner_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_comment_partner` (`partner_id`),
  KEY `fk_comment_user` (`user_id`),
  CONSTRAINT `fk_comment_partner` FOREIGN KEY (`partner_id`) REFERENCES `partner` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comment_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `applicant_id` bigint(20) NOT NULL COMMENT '申请人ID',
  `receiver_id` bigint(20) NOT NULL COMMENT '接收人ID',
  `partner_id` bigint(20) NOT NULL COMMENT '搭档信息ID',
  `status` varchar(20) DEFAULT 'pending' COMMENT '状态：pending(待处理)、accepted(已接受)、rejected(已拒绝)',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_application_applicant` (`applicant_id`),
  KEY `fk_application_receiver` (`receiver_id`),
  KEY `fk_application_partner` (`partner_id`),
  CONSTRAINT `fk_application_applicant` FOREIGN KEY (`applicant_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_application_receiver` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_application_partner` FOREIGN KEY (`partner_id`) REFERENCES `partner` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;