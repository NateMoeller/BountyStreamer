import React, { Component } from 'react';
import LoadingComponent from '../common/loading/LoadingComponent';
import ProfileComponent from './ProfileComponent';
import PropTypes from 'prop-types';
import { Redirect } from 'react-router-dom';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import { profileOperations } from './duck';

/* global sessionStorage */
class ProfileContainer extends Component {
  componentWillMount() {
    const { getUser } = this.props;
    if (!sessionStorage.getItem('user')) {
      getUser();
    }
  }

  render() {
    if (this.props.profile.redirect) {
      return (
        <Redirect to={{ pathname: '/login', state: { from: this.props.location } }} />
      );
    }
    const user = JSON.parse(sessionStorage.getItem('user'));

    if (!user) {
      return <LoadingComponent />;
    }

    return (
      <ProfileComponent
        user={user}
        testAlert={this.props.testAlert}
        alertChannelId={user.alertChannelId}
        listMyDonations={this.props.listMyDonations}
        listMyBounties={this.props.listMyBounties}
        voteBounty={this.props.voteBounty}
        bounties={this.props.profile.bounties}
        donations={this.props.profile.donations}
        totalBounties={this.props.profile.totalBounties}
        totalDonations={this.props.profile.totalDonations}
        showSpinner={this.props.profile.showSpinner}
        acceptBounty={this.props.acceptBounty}
        removeBounty={this.props.removeBounty}
      />
    );
  }
}

function mapDispatchToProps(dispatch) {
  return bindActionCreators({ ...profileOperations }, dispatch);
}

function mapStateToProps(state) {
  return {
    profile: state.profile
  };
}

ProfileContainer.propTypes = {
  getUser: PropTypes.func.isRequired,
  profile: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired,
  listMyDonations: PropTypes.func.isRequired,
  listMyBounties: PropTypes.func.isRequired,
  voteBounty: PropTypes.func.isRequired
};

export default connect(mapStateToProps, mapDispatchToProps)(ProfileContainer);
