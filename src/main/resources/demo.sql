DROP TABLE IF EXISTS "public"."demo";

CREATE TABLE "public"."demo" (
    "id" varchar,
    "code" varchar,
    "detail" jsonb,
    "sub_model" jsonb
);

INSERT INTO "public"."demo" ("id", "code", "detail", "sub_model") VALUES ('id001', 'test2', '{"test": "test3"}', '{"subId":"ddd1","subCode":"ttt1"}');

INSERT INTO "public"."demo" ("id", "code", "detail", "sub_model") VALUES ('id002', 'test3', '{"test": "test4"}', '{"subId":"ddd2","subCode":"ttt2"}');