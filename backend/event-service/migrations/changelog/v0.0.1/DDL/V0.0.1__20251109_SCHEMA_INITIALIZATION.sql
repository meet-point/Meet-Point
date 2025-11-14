CREATE TABLE IF NOT EXISTS organization_data
(
    organization_id UUID,
    label           VARCHAR(100),
    preview         TEXT,
    description     TEXT,
    city            VARCHAR(100),
    address         VARCHAR(100),
    --------------------------------------------------------------------------------------------------------------------
    CONSTRAINT org_data_organization_id_pk PRIMARY KEY (organization_id),
    CONSTRAINT org_data_label_nn CHECK (label IS NOT NULL),
    CONSTRAINT org_data_preview_nn CHECK (preview IS NOT NULL),
    CONSTRAINT org_data_description_nn CHECK (description IS NOT NULL)
);

CREATE TABLE IF NOT EXISTS organization_additional_info
(
    organization_id              UUID,
    contact_email                VARCHAR(100),
    contact_phone                VARCHAR(100),
    events_description           TEXT,
    completed_events_description TEXT,
    locations_description        TEXT,
    creator_id                   UUID,
    created_at                   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update_by               UUID,
    last_update_at               TIMESTAMP,
    --------------------------------------------------------------------------------------------------------------------
    CONSTRAINT org_add_info_organization_id_pk PRIMARY KEY (organization_id),
    CONSTRAINT org_add_info_organization_id_fk FOREIGN KEY (organization_id) REFERENCES organization_data (organization_id),
    CONSTRAINT org_add_info_contact_email_uq UNIQUE (contact_email),
    CONSTRAINT org_add_info_contact_phone_uq UNIQUE (contact_phone),
    CONSTRAINT org_add_info_events_description_nn CHECK (events_description IS NOT NULL),
    CONSTRAINT org_add_info_completed_events_description_nn CHECK (completed_events_description IS NOT NULL),
    CONSTRAINT org_add_info_locations_description_nn CHECK (locations_description IS NOT NULL),
    CONSTRAINT org_add_info_creator_id_nn CHECK (creator_id IS NOT NULL),
    CONSTRAINT org_add_info_created_at_nn CHECK (created_at IS NOT NULL)
);

CREATE TABLE IF NOT EXISTS organization_manager
(
    organization_id UUID,
    manager_id      UUID,
    date_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    --------------------------------------------------------------------------------------------------------------------
    CONSTRAINT org_manager_pk PRIMARY KEY (organization_id, manager_id),
    CONSTRAINT org_manager_organization_id_fk FOREIGN KEY (organization_id) REFERENCES organization_data (organization_id),
    CONSTRAINT org_manager_date_time_nn CHECK (date_time IS NOT NULL)
);

CREATE TABLE IF NOT EXISTS location_data
(
    location_id     UUID,
    organization_id UUID,
    label           VARCHAR(100),
    preview         TEXT,
    description     TEXT,
    city            VARCHAR(100),
    address         VARCHAR(100),
    public_status   VARCHAR(20),
    --------------------------------------------------------------------------------------------------------------------
    CONSTRAINT loc_data_location_id_pk PRIMARY KEY (location_id),
    CONSTRAINT loc_data_organization_id_fk FOREIGN KEY (organization_id) REFERENCES organization_data (organization_id),
    CONSTRAINT loc_data_label_nn CHECK (label IS NOT NULL),
    CONSTRAINT loc_data_preview_nn CHECK (preview IS NOT NULL),
    CONSTRAINT loc_data_description_nn CHECK (description IS NOT NULL),
    CONSTRAINT loc_data_public_status_nn CHECK (public_status IS NOT NULL)
);

CREATE TABLE IF NOT EXISTS location_additional_info
(
    location_id                  UUID,
    events_description           TEXT,
    completed_events_description TEXT,
    organization_description        TEXT,
    creator_id                   UUID,
    created_at                   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update_by               UUID,
    last_update_at               TIMESTAMP,
    ---------------------------------------------------------------------------------------------------------
    CONSTRAINT loc_add_info_location_id_pk PRIMARY KEY (location_id),
    CONSTRAINT loc_add_info_location_id_fk FOREIGN KEY (location_id) REFERENCES location_data (location_id),
    CONSTRAINT loc_add_info_events_description_nn CHECK (events_description IS NOT NULL),
    CONSTRAINT loc_add_info_completed_events_description_nn CHECK (completed_events_description IS NOT NULL),
    CONSTRAINT loc_add_info_organization_description_nn CHECK (organization_description IS NOT NULL),
    CONSTRAINT loc_add_info_creator_id_nn CHECK (creator_id IS NOT NULL),
    CONSTRAINT loc_add_info_created_at_nn CHECK (created_at IS NOT NULL)
);

CREATE TABLE IF NOT EXISTS category_type
(
    type_id     UUID,
    label       VARCHAR(100),
    preview     TEXT,
    description TEXT,
    ------------------------------------------------------------------
    CONSTRAINT ctg_type_type_id_pk PRIMARY KEY (type_id),
    CONSTRAINT ctg_type_label_nn CHECK (label IS NOT NULL),
    CONSTRAINT ctg_type_preview_nn CHECK (preview IS NOT NULL),
    CONSTRAINT ctg_type_description_nn CHECK (description IS NOT NULL)
);

CREATE TABLE IF NOT EXISTS category_data
(
    category_id UUID,
    type_id     UUID,
    label       VARCHAR(100),
    preview     TEXT,
    description TEXT,
    ----------------------------------------------------------------------------------------
    CONSTRAINT ctg_data_category_id_pk PRIMARY KEY (category_id),
    CONSTRAINT ctg_data_type_id_fk FOREIGN KEY (type_id) REFERENCES category_type (type_id),
    CONSTRAINT ctg_data_label_nn CHECK (label IS NOT NULL),
    CONSTRAINT ctg_data_preview_nn CHECK (preview IS NOT NULL),
    CONSTRAINT ctg_data_description_nn CHECK (description IS NOT NULL)
);

CREATE TABLE IF NOT EXISTS event_data
(
    event_id           UUID,
    location_id        UUID,
    category_id        UUID,
    label              VARCHAR(100),
    preview            TEXT,
    description        TEXT,
    date_time          TIMESTAMP,
    cost               DECIMAL(10,2),
    max_allowed_people SMALLINT,
    registered_now     SMALLINT,
    access_status      VARCHAR(20),
    publish_status     VARCHAR(20),
    ------------------------------------------------------------------------------------------------------
    CONSTRAINT event_data_event_id_pk PRIMARY KEY (event_id),
    CONSTRAINT event_data_location_id_fk FOREIGN KEY (location_id) REFERENCES location_data (location_id),
    CONSTRAINT event_data_category_id_fk FOREIGN KEY (category_id) REFERENCES category_data (category_id),
    CONSTRAINT event_data_label_nn CHECK (label IS NOT NULL),
    CONSTRAINT event_data_preview_nn CHECK (preview IS NOT NULL),
    CONSTRAINT event_data_description_nn CHECK (description IS NOT NULL),
    CONSTRAINT event_data_access_status_nn CHECK (access_status IS NOT NULL),
    CONSTRAINT event_data_publish_status_nn CHECK (publish_status IS NOT NULL)
);

CREATE TABLE IF NOT EXISTS event_additional_info
(
    event_id         UUID,
    publication_time TIMESTAMP,
    creator_id       UUID,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update_by   UUID,
    last_update_at   TIMESTAMP,
    ---------------------------------------------------------------------------------------------------------
    CONSTRAINT event_add_info_event_id_pk PRIMARY KEY (event_id),
    CONSTRAINT event_add_info_event_id_fk FOREIGN KEY (event_id) REFERENCES event_data (event_id),
    CONSTRAINT event_add_info_publication_time_nn CHECK (publication_time IS NOT NULL),
    CONSTRAINT event_add_info_creator_id_nn CHECK (creator_id IS NOT NULL),
    CONSTRAINT event_add_info_created_at_nn CHECK (created_at IS NOT NULL)
);



