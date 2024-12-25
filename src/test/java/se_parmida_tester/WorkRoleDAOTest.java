package se_parmida_tester;

import org.junit.jupiter.api.Test;
import se_parmida_jdbc.WorkRole;
import se_parmida_jdbc.WorkRoleDAO;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//testet kontrollerar att "insertworkrole" lägger till ny roll och
// att "getallworkroles" kan hämta toller.
class WorkRoleDAOTest {
    @Test
    void testInsertAndRetrieve() {
        WorkRoleDAO dao = new WorkRoleDAO();

        // Skapa en ny roll, sätter egenskaper som title, beskrivning, lön och datum.
        WorkRole role = new WorkRole();
        role.setTitle("Mjukvaruingenjör");
        role.setDescription("Utvecklar mjukvara");
        role.setSalary(50000);
        role.setCreationDate(new java.util.Date());
        dao.insertWorkRole(role);

        // Hämta alla roller som en lista, ser till att listan inte är null,
        // ser till att innehållet i listan är minst mer än 0.
        List<WorkRole> roles = dao.getAllWorkRoles();
        assertNotNull(roles);
        assertTrue(roles.size() > 0);

        // Kontrollera att det finns en roll med rätt titel
        boolean found = false;
        for (WorkRole workRole : roles) {
            if ("Mjukvaruingenjör".equals(workRole.getTitle())) {
                found = true;
                break; // Avslutar loopen när rätt roll hittas
            }
        }
        // kontrollerar att rollen hittas.
        assertEquals(true, found, "Arbetsrollen 'Mjukvaruingenjör' hittades inte.");
    }
}
