package com.nicknathanjustin.streamercontracts.contracts;

import com.nicknathanjustin.streamercontracts.contracts.dtos.ContractDto;
import com.nicknathanjustin.streamercontracts.users.UserModel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private static final int PAY_PAL_TRANSACTION_TIMEOUT_IN_DAYS = 3;

    @NonNull final ContractModelRepository contractModelRepository;

    @Override
    public ContractModel createContract(@NonNull final UserModel proposer, @NonNull final UserModel streamer, @Nullable final String game, @NonNull final String description) {
        final Timestamp creationTimestamp = new Timestamp(System.currentTimeMillis());
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(creationTimestamp);
        calendar.add(Calendar.DAY_OF_WEEK, PAY_PAL_TRANSACTION_TIMEOUT_IN_DAYS);
        final Timestamp settlesTimestamp = new Timestamp(calendar.getTime().getTime());

        return contractModelRepository.save(ContractModel.builder()
                .proposer(proposer)
                .streamer(streamer)
                .game(game)
                .description(description)
                .proposedAt(creationTimestamp)
                .acceptedAt(null)
                .declinedAt(null)
                .settlesAt(settlesTimestamp)
                .expiredAt(null)
                .completedAt(null)
                .failedAt(null)
                .disputedAt(null)
                .isCommunityContract(false)
                .state(ContractState.OPEN)
                .devNote(null)
                .build());
    }

    @Override
    public Set<ContractModel> getSettleableContracts() {
        return contractModelRepository.findAllSettleableContracts(new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public Optional<ContractModel> getContract(@NonNull final UUID contractId) {
        return contractModelRepository.findById(contractId);
    }

    @Override
    public void setContractState(@NonNull final ContractModel contractModel, final ContractState newContractState) {
        // TODO: loop through donations for the contract and either void or capture payPalPayments when the contract
        // transitions to a "payable" state
        contractModel.setContractState(newContractState);
        contractModelRepository.save(contractModel);
    }

    @Override
    public Page<ContractDto> getContractsForStreamerAndState(@NonNull final UserModel user, @NonNull final ContractState state, @NonNull final Pageable pageable) {
        return contractModelRepository.findAllContractsForStreamerAndState(
                user.getTwitchUsername(),
                state,
                pageable);
    }

    @Override
    public Page<ContractDto> getContractsForStreamer(@NonNull final UserModel user, @NonNull final Pageable pageable) {
        return contractModelRepository.findAllContractsForStreamer(user.getTwitchUsername(), pageable);
    }

    @Override
    public Page<ContractDto> getContractsForState(@NonNull final ContractState state, @NonNull final Pageable pageable) {
        return contractModelRepository.findAllContractsForState(state, pageable);
    }

    @Override
    public Page<ContractDto> getAllContracts(@NonNull final Pageable pageable) {
        return contractModelRepository.findAllContracts(pageable);
    }

    @Override
    public Page<ContractDto> getContractsForDonatorAndState(@NonNull final UserModel donator, @NonNull final ContractState state, @NonNull final Pageable pageable) {
        return contractModelRepository.findAllContractsForDonatorAndState(donator.getTwitchUsername(), state, pageable);
    }

    @Override
    public Page<ContractDto> getContractsForDonator(@NonNull final UserModel donator, @NonNull final Pageable pageable) {
        return contractModelRepository.findAllContractsForDonator(donator.getTwitchUsername(), pageable);
    }

    @Override
    public long countByStateAndStreamer(@NonNull final ContractState state, @NonNull final UserModel streamer) {
        return contractModelRepository.countByStateAndStreamer(state, streamer);
    }

    @Override
    public BigDecimal getMoneyEarnedForStreamer(UserModel streamer) {
        return contractModelRepository.getMoneyEarnedForStreamer(streamer.getTwitchUsername());
    }
}
