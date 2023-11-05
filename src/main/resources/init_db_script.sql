CREATE TABLE stud_plan
(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    stud_objects character varying(255)[6] NOT NULL,
    CONSTRAINT stud_plan_pk PRIMARY KEY (id)
);

CREATE TABLE stud_group
(
	id bigserial NOT NULL,
	name character varying(255),
	stud_plan_id bigint,
	CONSTRAINT stud_group_pkey PRIMARY KEY (id),
	CONSTRAINT fk_stud_plan_id FOREIGN KEY (stud_plan_id)
		REFERENCES stud_plan (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE progress
(
	id bigserial NOT NULL,
	grades integer[6] NOT NULL,
	CONSTRAINT progress_pkey PRIMARY KEY (id)
);

CREATE TABLE student
(
	id bigserial NOT NULL,
	family character varying(255) NOT NULL,
	name character varying(255) NOT NULL,
	age integer NOT NULL,
	stud_group_id bigint,
	progress_id bigint,
	CONSTRAINT student_pkey PRIMARY KEY (id),
	CONSTRAINT fk_stud_group_id FOREIGN KEY (stud_group_id)
		REFERENCES stud_group (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_progress_id FOREIGN KEY (progress_id)
		REFERENCES progress (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);
