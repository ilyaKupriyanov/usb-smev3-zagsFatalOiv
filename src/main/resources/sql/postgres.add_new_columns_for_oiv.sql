alter table fnsbroadcast.death
ADD column versionDate timestamp without time zone,
ADD column versionNumber character varying(3),
ADD column deathCertsRepeat character varying(1000),
ADD column unindentPerson boolean,
ADD column aboutPerson character varying(200),
ADD column aboutDeath character varying(200),
ADD column childDeath character varying(100),
ADD column doctor character varying(800),
ADD column applicantInfo character varying(5000),
ADD column recordDate timestamp without time zone;

# changes for existing fields
ALTER TABLE fnsbroadcast.death ALTER COLUMN birthDate TYPE character varying(100);
ALTER TABLE fnsbroadcast.death ALTER COLUMN deathActChangesInfo TYPE character varying(15000);
ALTER TABLE fnsbroadcast.death ALTER COLUMN deathDate TYPE character varying(100);
ALTER TABLE fnsbroadcast.death ALTER COLUMN deathDate DROP NOT NULL;
ALTER TABLE fnsbroadcast.death ALTER COLUMN deathTime DROP NOT NULL;
ALTER TABLE fnsbroadcast.death ALTER COLUMN deathPlace DROP NOT NULL;
