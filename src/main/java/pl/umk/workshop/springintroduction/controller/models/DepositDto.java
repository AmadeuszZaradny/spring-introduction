package pl.umk.workshop.springintroduction.controller.models;

import java.util.List;

public record DepositDto(StudentDto student, List<String> items) {

}
