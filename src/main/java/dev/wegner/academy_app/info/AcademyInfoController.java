package dev.wegner.academy_app.info;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class AcademyInfoController
{
    private final AcademyInfoProvider academyInfoProvider;

    public AcademyInfoController( AcademyInfoProvider academyInfoProvider )
    {
        this.academyInfoProvider = academyInfoProvider;
    }

    @GetMapping
    public AcademyInfo getAcademyInfo()
    {
        return academyInfoProvider.getAcademyInfo();
    }
}