package com.nicknathanjustin.streamercontracts.contracts;

import com.nicknathanjustin.streamercontracts.contracts.dtos.ContractDto;
import com.nicknathanjustin.streamercontracts.users.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ContractModelRepository extends CrudRepository<ContractModel, UUID> {

    long countByStateAndStreamer(ContractState state, UserModel streamer);

    @Query("SELECT contractModel " +
           "FROM ContractModel contractModel " +
           "WHERE contractModel.settlesAt < :currentTimestamp " +
           "AND contractModel.state = 'ACCEPTED'")
    Set<ContractModel> findAllSettleableContracts(@Param("currentTimestamp") Timestamp now);

    @Query("SELECT new com.nicknathanjustin.streamercontracts.contracts.dtos.ContractDto(" +
                "contractModel) " +
           "FROM ContractModel contractModel " +
           "WHERE contractModel.state = :state " +
           "AND contractModel.streamer.twitchUsername = :streamer")
    Page<ContractDto> findAllContractsForStreamerAndState(@Param("streamer") String streamer, @Param("state") ContractState state, Pageable pageable);

    @Query("SELECT new com.nicknathanjustin.streamercontracts.contracts.dtos.ContractDto(" +
                "contractModel) " +
           "FROM ContractModel contractModel " +
           "WHERE contractModel.streamer.twitchUsername = :streamer")
    Page<ContractDto> findAllContractsForStreamer(@Param("streamer") String streamer, Pageable pageable);

    @Query("SELECT new com.nicknathanjustin.streamercontracts.contracts.dtos.ContractDto(" +
                "contractModel) " +
           "FROM ContractModel contractModel " +
           "WHERE contractModel.state = :state ")
    Page<ContractDto> findAllContractsForState(@Param("state") ContractState state, Pageable pageable);

    @Query("SELECT new com.nicknathanjustin.streamercontracts.contracts.dtos.ContractDto(" +
                "contractModel) " +
           "FROM ContractModel contractModel ")
    Page<ContractDto> findAllContracts(Pageable pageable);

    @Query("SELECT new com.nicknathanjustin.streamercontracts.contracts.dtos.ContractDto(" +
                "donationModel.contract) " +
           "FROM DonationModel donationModel " +
           "WHERE donationModel.donator.twitchUsername = :donator " +
           "AND donationModel.contract.state = :state")
    Page<ContractDto> findAllContractsForDonatorAndState(@Param("donator") String donator, @Param("state") ContractState state, Pageable pageable);

    @Query("SELECT new com.nicknathanjustin.streamercontracts.contracts.dtos.ContractDto(" +
                "donationModel.contract) " +
           "FROM DonationModel donationModel " +
           "WHERE donationModel.donator.twitchUsername = :donator ")
    Page<ContractDto> findAllContractsForDonator(@Param("donator") String donator, Pageable pageable);

    @Query("SELECT SUM(d.donationAmount) FROM DonationModel d WHERE d.contract.state = 4")
    BigDecimal getMoneyEarnedForStreamer(@Param("streamer") String streamer);
}
