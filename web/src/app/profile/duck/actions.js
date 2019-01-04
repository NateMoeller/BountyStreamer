import {
  RECEIVE_USER_INFO_FAILURE,
  RECEIVE_USER_INFO_SUCCESS,
  REQUEST_USER_INFO,
  REQUEST_TEST_ALERT,
  RECEIVE_TEST_ALERT,
  REQUEST_MY_DONATIONS,
  RECEIVE_MY_DONATIONS,
  REQUEST_MY_BOUNTIES,
  RECEIVE_MY_BOUNTIES,
  REQUEST_ACCEPT_BOUNTY,
  RECEIVE_ACCEPT_BOUNTY_SUCCESS,
  RECEIVE_ACCEPT_BOUNTY_FAILURE,
  REQUEST_REMOVE_BOUNTY,
  RECEIVE_REMOVE_BOUNTY_SUCCESS,
  RECEIVE_REMOVE_BOUNTY_FAILURE,
  REQUEST_VOTE_BOUNTY,
  RECEIVE_VOTE_BOUNTY,
  REQUEST_ACTIVE_BOUNTIES,
  RECEIVE_ACTIVE_BOUNTIES
} from './types';

const requestUserInfo = () => ({
  type: REQUEST_USER_INFO
});

const receiveUserInfoSuccess = userInfo => ({
  type: RECEIVE_USER_INFO_SUCCESS,
  userInfo
});

const receiveUserInfoFailure = error => ({
  type: RECEIVE_USER_INFO_FAILURE,
  error
});

const requestTestAlert = () => ({
  type: REQUEST_TEST_ALERT
});

const receiveTestAlert = (success) => ({
  type: RECEIVE_TEST_ALERT,
  success
});

const requestMyDonations = () => ({
  type: REQUEST_MY_DONATIONS
});

const receiveMyDonations = (data) => ({
  type: RECEIVE_MY_DONATIONS,
  data
});

const requestMyBounties = () => ({
  type: REQUEST_MY_BOUNTIES
});

const receiveMyBounties = (data) => ({
  type: RECEIVE_MY_BOUNTIES,
  data
});

const requestAcceptBounty = () => ({
  type: REQUEST_ACCEPT_BOUNTY
});

const receiveAcceptBountySuccess = () => ({
  type: RECEIVE_ACCEPT_BOUNTY_SUCCESS
});

const receiveAcceptBountyFailure = () => ({
  type: RECEIVE_ACCEPT_BOUNTY_FAILURE
});

const requestRemoveBounty = () => ({
  type: REQUEST_REMOVE_BOUNTY
});

const receiveRemoveBountySuccess = () => ({
  type: RECEIVE_REMOVE_BOUNTY_SUCCESS
});

const receiveRemoveBountyFailure = () => ({
  type: RECEIVE_REMOVE_BOUNTY_FAILURE
});

const requestVoteBounty = () => ({
  type: REQUEST_VOTE_BOUNTY
});

const receiveVoteBounty = () => ({
  type: RECEIVE_VOTE_BOUNTY
});

const requestActiveBounties = () => ({
  type: REQUEST_ACTIVE_BOUNTIES
});

const receiveActiveBounties = (data) => ({
  type: RECEIVE_ACTIVE_BOUNTIES,
  data
});

export {
  requestUserInfo,
  receiveUserInfoSuccess,
  receiveUserInfoFailure,
  requestTestAlert,
  receiveTestAlert,
  requestMyDonations,
  receiveMyDonations,
  requestMyBounties,
  receiveMyBounties,
  requestAcceptBounty,
  receiveAcceptBountySuccess,
  receiveAcceptBountyFailure,
  requestRemoveBounty,
  receiveRemoveBountySuccess,
  receiveRemoveBountyFailure,
  requestVoteBounty,
  receiveVoteBounty,
  requestActiveBounties,
  receiveActiveBounties
};
