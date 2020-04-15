DROP TABLE IF EXISTS FNSBROADCAST.DEATH;

CREATE TABLE FNSBROADCAST.DEATH
(
  id SERIAL PRIMARY KEY,
  regInfoId character varying(36) NOT NULL,
  regInfoDate timestamp without time zone NOT NULL,
  regDate timestamp without time zone NOT NULL,
  regNumber character varying(22) NOT NULL,
  zagsName character varying(1000) NOT NULL,
  zagsCode character varying(8) NOT NULL,
  stateDesc character varying(255) NOT NULL,
  stateCode character varying(2) NOT NULL,
  stateDate timestamp without time zone NOT NULL,
  deathCerts character varying(1500),
  deathCause character varying(3000),
  surname character varying(100),
  firstname character varying(100),
  middlename character varying(100),
  sex character varying(1),
  citizenshipOKSM character varying(3),
  citizenshipCountryName character varying(255),
  birthDate character varying(20),
  birthPlace character varying(1050),
  addressRF character varying(3000),
  addressRFText character varying(512),
  addressNotRF character varying(800),
  identityDocInfo character varying(550),
  deathDate character varying(20) NOT NULL,
  deathTime character varying(20) NOT NULL,
  deathPlace character varying(1050) NOT NULL,
  deathDocument character varying(1024),
  deathDocIssuer character varying(255),
  deathDocFIOIP character varying(250),
  deathDocFIOFL character varying(250),
  deathActChangesInfo character varying(6000),
  smevMessageId character varying(50) NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE FNSBROADCAST.DEATH OWNER TO fnsbroadcast;

CREATE INDEX FNSBROADCAST_DEATH_ID ON FNSBROADCAST.DEATH USING btree(id);
CREATE INDEX FNSBROADCAST_DEATH_SMEV_MESSAGE_ID ON FNSBROADCAST.DEATH USING btree(smevMessageId);