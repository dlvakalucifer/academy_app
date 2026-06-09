package dev.wegner.academy_app.info;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AcademyInfoTest {

    @Test
    void shouldCreateAcademyInfo() {

        AcademyInfo info = new AcademyInfo("academy_app", "1.0.0", "21", "4", "PostgreSQL", "MinIO", "Redis");

        assertThat(info.application()).isEqualTo("academy_app");

        assertThat(info.version()).isEqualTo("1.0.0");

        assertThat(info.database()).isEqualTo("PostgreSQL");

        assertThat(info.storage()).isEqualTo("MinIO");

        assertThat(info.cache()).isEqualTo("Redis");
    }
}