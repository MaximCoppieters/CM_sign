package be.pxl.util;

import be.pxl.data.model.Dossier;
import be.pxl.data.model.Invitee;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DossierMapperTest {

    @Test
    public void toJson() {
        DossierMapper mapper = new DossierMapper();

        List<Invitee> invitees = Arrays.asList(new Invitee());
    }
}