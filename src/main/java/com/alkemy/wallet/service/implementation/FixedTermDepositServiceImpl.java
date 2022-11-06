package com.alkemy.wallet.service.implementation;

import com.alkemy.wallet.dto.FixedTermDepositDto;
import com.alkemy.wallet.exception.FixedTermDepositException;
import com.alkemy.wallet.mapper.FixedTermDepositMapper;
import com.alkemy.wallet.model.Account;
import com.alkemy.wallet.model.FixedTermDeposit;
import com.alkemy.wallet.repository.FixedTermDepositRepository;
import com.alkemy.wallet.service.FixedTermDepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional()
@RequiredArgsConstructor
public class FixedTermDepositServiceImpl implements FixedTermDepositService {
    @Autowired
    private FixedTermDepositRepository fixedTermDepositRepository;
    private final FixedTermDepositMapper mapper;

    @Override
    public FixedTermDepositDto createFixedTermDeposit(FixedTermDepositDto fixedTermDepositDto) throws FixedTermDepositException {
        FixedTermDeposit fixedTermDeposit=new FixedTermDeposit(mapper.convertToEntity(fixedTermDepositDto));
        Timestamp timestamp=new Timestamp(new Date().getTime());
        fixedTermDeposit.setCreationDate(timestamp);

        Long days= ((fixedTermDeposit.getClosingDate().getTime())-fixedTermDeposit.getCreationDate().getTime())/86400000 ;
           if(days<30){
               throw new FixedTermDepositException();
           }

        fixedTermDeposit.setInterest(0.5*days);
        fixedTermDepositRepository.save(fixedTermDeposit);
        return fixedTermDepositDto;
    }

    @Override
    public List<FixedTermDepositDto> getAccountFixedTermDeposits(int accountId) {
        Account account = new Account(accountId);
        return fixedTermDepositRepository.findallByAccount(account).stream()
                .map(mapper::convertToDto).collect(Collectors.toList());
    }
}
