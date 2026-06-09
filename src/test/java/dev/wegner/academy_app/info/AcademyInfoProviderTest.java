package dev.wegner.academy_app.info;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AcademyInfoProviderTest
{

    @Test
    void shouldLoadInfoFromJson() throws Exception
    {
        var provider = new AcademyInfoProvider();
        var info = provider.getAcademyInfo();

        assertThat(info.application()).isEqualTo("academy_app");
    }
}
