CREATE TABLE IF NOT EXISTS user_data
(
    user_id       UUID PRIMARY KEY,
    first_name    VARCHAR(100),
    middle_name   VARCHAR(100),
    last_name     VARCHAR(100),
    email         VARCHAR(254),
    phone_number  VARCHAR(20),
    state         VARCHAR(20),
    last_login    TIMESTAMP,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP
);

CREATE TABLE user_credentials
(
    credential_id UUID,
    user_id       UUID,
    provider_type VARCHAR(20),
    provider_key  VARCHAR(255) NOT NULL,
    hash_password VARCHAR,
    -------------------------------------------------------------------------------
    CONSTRAINT acc_cr_credentials_id_pk PRIMARY KEY (credential_id),
    CONSTRAINT acc_cr_user_id_nn CHECK ( user_id IS NOT NULL ),
    CONSTRAINT acc_cr_user_id_fk FOREIGN KEY (user_id) REFERENCES user_data (user_id),
    CONSTRAINT acc_cr_provider_type_nn CHECK ( provider_type IS NOT NULL ),
    CONSTRAINT acc_cr_provider_key_nn CHECK ( provider_key IS NOT NULL ),
    CONSTRAINT unique_provider_pair UNIQUE (provider_type, provider_key)
);

CREATE TABLE user_role
(
    user_id UUID,
    role    VARCHAR(20),
    -----------------------------------------------------------------------------
    CONSTRAINT us_pk PRIMARY KEY (user_id, role),
    CONSTRAINT us_r_user_id_nn CHECK (user_id IS NOT NULL),
    CONSTRAINT us_r_user_id_fk FOREIGN KEY (user_id) REFERENCES user_data (user_id),
    CONSTRAINT us_r_role_nn CHECK (role IS NOT NULL )
);