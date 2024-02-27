package kz.aitu.se2311.oopproject.companies;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.aitu.se2311.oopproject.companies.dto.requests.CompanyCreationRequest;
import kz.aitu.se2311.oopproject.companies.dto.requests.CompanyDeletionRequest;
import kz.aitu.se2311.oopproject.companies.dto.requests.CompanyEditionRequest;
import kz.aitu.se2311.oopproject.companies.dto.responses.CompanyDeletionResponse;
import kz.aitu.se2311.oopproject.companies.dto.responses.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
@Tag(name = "Company controller")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/get/{companySlug}")
    @Operation(summary = "Get company by slug", responses = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CompanyResponse.class))}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(example = """
                    {
                        "message": "Not found"
                    }
                    """))})
    })
    public ResponseEntity<CompanyResponse> getCompanyByName(final @PathVariable String companySlug) {
        CompanyResponse companyResponse = companyService.getCompanyBySlug(companySlug);
        return ResponseEntity.ok(companyResponse);
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get all companies", responses = {
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CompanyResponse.class)))})
    })
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @PostMapping("/create")
    @PutMapping("/edit")
    @Operation(summary = "Create company by name and description", responses = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CompanyResponse.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(example = """
                    {
                        "message": "JSON validation error. Check the correctness of the JSON"
                    }
                    """))})
            // TODO: Make http response for 409
    })
    public ResponseEntity<CompanyResponse> createCompany(final @RequestBody @Valid CompanyCreationRequest request) {
        return ResponseEntity.ok(companyService.createCompany(request));
    }

    @PutMapping("/edit")
    @Operation(summary = "Edit company fields by id", description = "Fields are optional.", responses = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CompanyResponse.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(example = """
                    {
                        "message": "JSON validation error. Check the correctness of the JSON"
                    }
                    """))}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(example = """
                    {
                        "message": "Not found"
                    }
                    """))})
    })
    public ResponseEntity<CompanyResponse> editCompany(final @RequestBody @Valid CompanyEditionRequest request) {
        return ResponseEntity.ok(companyService.editCompany(request));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete company by id", responses = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CompanyDeletionRequest.class))}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(example = """
                    {
                        "message": "Not found"
                    }
                    """))})
    })
    public ResponseEntity<CompanyDeletionResponse> editCompany(final @RequestBody @Valid CompanyDeletionRequest request) {
        return ResponseEntity.ok(companyService.deleteCompany(request));
    }
}
