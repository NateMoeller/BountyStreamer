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
        twitchUserName={user.displayName}
        imageUrl={user.profileImageUrl}
        testAlert={this.props.testAlert}
        alertChannelId={user.alertChannelId}
        listOpenDonations={this.props.listOpenDonations}
        updateContract={this.props.updateContract}
        openBounties={this.props.profile.openBounties}
        totalOpenDonations={this.props.profile.totalOpenDonations}
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
  listOpenDonations: PropTypes.func.isRequired,
  updateContract: PropTypes.func.isRequired
};

export default connect(mapStateToProps, mapDispatchToProps)(ProfileContainer);
