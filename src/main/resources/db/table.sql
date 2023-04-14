
-- user table 설계
CREATE TABLE user_tb(
	id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(30) NOT NULL,
    fullname VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

-- 계좌 정보 테이블
CREATE TABLE account_tb(
	id INT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    balance BIGINT NOT NULL COMMENT '잔액',
    user_id INT,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

-- 입출금 내역 저장
-- 유저간 이체 내역 저장
-- 계좌 히스토리 정보 테이블
-- BIGINT <- 8바이트 크기 정수형
CREATE TABLE history_tb(
	id INT AUTO_INCREMENT PRIMARY KEY COMMENT '거래 내역 식별자',
    amount BIGINT NOT NULL COMMENT '거래 금액',
    w_account_id INT COMMENT '출금 계좌 id',
    d_account_id INT COMMENT '입금 계좌 id',
    w_balance BIGINT COMMENT '출금요청된 계좌의 잔액',
    d_balance BIGINT COMMENT '입금요청된 계좌의 잔액',
    created_at TIMESTAMP NOT NULL DEFAULT now()
);