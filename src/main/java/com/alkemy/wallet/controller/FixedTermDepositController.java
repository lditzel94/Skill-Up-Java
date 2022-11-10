package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.FixedTermDepositDto;
import com.alkemy.wallet.dto.FixedTermDepositSimulateDto;
import com.alkemy.wallet.exception.FixedTermDepositException;
import com.alkemy.wallet.service.FixedTermDepositService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fixedDeposit")
public class FixedTermDepositController {
    @Autowired
    private final FixedTermDepositService fixedTermDepositService;

    //Swagger Notation createFixedTermDeposit
    @Operation(summary = "Create FixedTermDeposit for logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "FixedTermDeposit created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FixedTermDepositDto.class)) })
    })
    //End Swagger notation
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER_ROLE')")
    public  ResponseEntity< FixedTermDepositDto > createFixedTermDeposit(@RequestBody FixedTermDepositDto fixedTermDepositDto, @RequestHeader("Authorization") String token) throws FixedTermDepositException {
        fixedTermDepositDto.setClosingDate(new Timestamp(fixedTermDepositDto.getClosingDate().getTime()+86400000));
        return ResponseEntity.ok(fixedTermDepositService.createFixedTermDeposit(fixedTermDepositDto, token));

    }



    //Swagger Notation simulateFixedTermDeposit
    @Operation(summary = "Simulate FixedTermDeposit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "FixedTermDeposit simulated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FixedTermDepositSimulateDto.class)) })
    })
    //End Swagger notation

    @GetMapping("/simulate")
    @PreAuthorize("hasRole('USER_ROLE')")
    public ResponseEntity<FixedTermDepositSimulateDto> simulateFixedTermDeposit(@RequestBody FixedTermDepositDto fixedTermDepositDto){
        fixedTermDepositDto.setClosingDate(new Timestamp(fixedTermDepositDto.getClosingDate().getTime()+86400000));
        return ResponseEntity.ok(fixedTermDepositService.simulateFixedTermDepositDto(fixedTermDepositDto));
    }






}
