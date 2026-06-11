package dev.wegner.academy_app.info;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class AcademyInfoProvider
{
    private final AcademyInfo academyInfo;

    public AcademyInfoProvider() throws IOException
    {
        try (InputStream in = getClass().getClassLoader()
                .getResourceAsStream("static/json/info.json"))
        {
            academyInfo = new ObjectMapper().readValue(in, AcademyInfo.class);
        }
    }

    public AcademyInfo getAcademyInfo()
    {
        return academyInfo;
    }
}
