package org.smartregister.chw.skeleton_sample.presenter;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.smartregister.chw.tbleprosy.contract.TBLeprosyProfileContract;
import org.smartregister.chw.tbleprosy.domain.MemberObject;
import org.smartregister.chw.tbleprosy.presenter.BaseTBLeprosyProfilePresenter;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class BaseTBLeprosyProfilePresenterTest {

    @Mock
    private TBLeprosyProfileContract.View view = Mockito.mock(TBLeprosyProfileContract.View.class);

    @Mock
    private TBLeprosyProfileContract.Interactor interactor = Mockito.mock(TBLeprosyProfileContract.Interactor.class);

    @Mock
    private MemberObject skeletonMemberObject = new MemberObject();

    private BaseTBLeprosyProfilePresenter profilePresenter = new BaseTBLeprosyProfilePresenter(view, interactor, skeletonMemberObject);


    @Test
    public void fillProfileDataCallsSetProfileViewWithDataWhenPassedMemberObject() {
        profilePresenter.fillProfileData(skeletonMemberObject);
        verify(view).setProfileViewWithData();
    }

    @Test
    public void fillProfileDataDoesntCallsSetProfileViewWithDataIfMemberObjectEmpty() {
        profilePresenter.fillProfileData(null);
        verify(view, never()).setProfileViewWithData();
    }

    @Test
    public void malariaTestDatePeriodIsLessThanSeven() {
        profilePresenter.recordSkeletonButton("");
        verify(view).hideView();
    }

    @Test
    public void malariaTestDatePeriodIsMoreThanFourteen() {
        profilePresenter.recordSkeletonButton("EXPIRED");
        verify(view).hideView();
    }

    @Test
    public void refreshProfileBottom() {
        profilePresenter.refreshProfileBottom();
        verify(interactor).refreshProfileInfo(skeletonMemberObject, profilePresenter.getView());
    }

    @Test
    public void saveForm() {
        profilePresenter.saveForm(null);
        verify(interactor).saveRegistration(null, view);
    }
}
