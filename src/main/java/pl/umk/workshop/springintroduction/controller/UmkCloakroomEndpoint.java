package pl.umk.workshop.springintroduction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.umk.workshop.springintroduction.controller.models.DepositDto;
import pl.umk.workshop.springintroduction.controller.models.DepositIdDto;
import pl.umk.workshop.springintroduction.controller.models.StudentDto;
import pl.umk.workshop.springintroduction.domain.UmkCloakroomFacade;
import pl.umk.workshop.springintroduction.domain.models.Item;
import pl.umk.workshop.springintroduction.domain.models.Student;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController()
@RequestMapping("/deposit")
public class UmkCloakroomEndpoint {

    private final UmkCloakroomFacade umkCloakroomFacade;

    @Autowired
    public UmkCloakroomEndpoint(UmkCloakroomFacade umkCloakroomFacade) {
        this.umkCloakroomFacade = umkCloakroomFacade;
    }


    /*
    Use this command to add some items to cloakroom

    curl --location --request POST 'http://localhost:8122/deposit' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "student": {
                "name": "Amadeusz",
                "surname": "Zaradny"
            },
            "items": [
                "JACKET"
            ]
        }'
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public DepositIdDto depositItems(@RequestBody DepositDto depositDto) {
        var deposit = umkCloakroomFacade.depositItems(
                mapStudent(depositDto.student()),
                mapItems(depositDto.items())
        );

        return new DepositIdDto(deposit.depositId().toString());
    }
    /*
    Use this command to collect some items from cloakroom (1 is an example id of deposit)

    curl --location --request DELETE 'http://localhost:8122/deposit/1'
     */
    @DeleteMapping
    @RequestMapping("/{id}")
    public void collectItems(@PathVariable String id) {
        umkCloakroomFacade.collectDeposit(Integer.parseInt(id));
    }

    private Student mapStudent(StudentDto studentDto) {
        return new Student(studentDto.name(), studentDto.surname());
    }

    private List<Item> mapItems(List<String> items) {
        return items.stream().map(value -> Item.valueOf(value.toUpperCase())).toList();
    }

}
