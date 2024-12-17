package org.smartregister.chw.tbleprosy.interactor;


import android.content.Context;

import androidx.annotation.VisibleForTesting;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.chw.tbleprosy.R;
import org.smartregister.chw.tbleprosy.TBLeprosyLibrary;
import org.smartregister.chw.tbleprosy.actionhelper.TBLeprosyActionHelper;
import org.smartregister.chw.tbleprosy.actionhelper.TBLeprosyMedicalHistoryActionHelper;
import org.smartregister.chw.tbleprosy.contract.BaseTBLeprosyVisitContract;
import org.smartregister.chw.tbleprosy.domain.MemberObject;
import org.smartregister.chw.tbleprosy.domain.VisitDetail;
import org.smartregister.chw.tbleprosy.model.BaseTBLeprosyVisitAction;
import org.smartregister.chw.tbleprosy.util.AppExecutors;
import org.smartregister.chw.tbleprosy.util.Constants;
import org.smartregister.sync.helper.ECSyncHelper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class BaseTBLeprosyServiceVisitInteractor extends BaseTBLeprosyVisitInteractor {

    protected BaseTBLeprosyVisitContract.InteractorCallBack callBack;

    String visitType;
    private final TBLeprosyLibrary TBLeprosyLibrary;
    private final LinkedHashMap<String, BaseTBLeprosyVisitAction> actionList;
    protected AppExecutors appExecutors;
    private ECSyncHelper syncHelper;
    private Context mContext;


    @VisibleForTesting
    public BaseTBLeprosyServiceVisitInteractor(AppExecutors appExecutors, TBLeprosyLibrary TBLeprosyLibrary, ECSyncHelper syncHelper) {
        this.appExecutors = appExecutors;
        this.TBLeprosyLibrary = TBLeprosyLibrary;
        this.syncHelper = syncHelper;
        this.actionList = new LinkedHashMap<>();
    }

    public BaseTBLeprosyServiceVisitInteractor(String visitType) {
        this(new AppExecutors(), TBLeprosyLibrary.getInstance(), TBLeprosyLibrary.getInstance().getEcSyncHelper());
        this.visitType = visitType;
    }

    @Override
    protected String getCurrentVisitType() {
        if (StringUtils.isNotBlank(visitType)) {
            return visitType;
        }
        return super.getCurrentVisitType();
    }

    @Override
    protected void populateActionList(BaseTBLeprosyVisitContract.InteractorCallBack callBack) {
        this.callBack = callBack;
        final Runnable runnable = () -> {
            try {
                evaluateSkeletonMedicalHistory(details);
                evaluateSkeletonPhysicalExam(details);
                evaluateSkeletonHTS(details);

            } catch (BaseTBLeprosyVisitAction.ValidationException e) {
                Timber.e(e);
            }

            appExecutors.mainThread().execute(() -> callBack.preloadActions(actionList));
        };

        appExecutors.diskIO().execute(runnable);
    }

    private void evaluateSkeletonMedicalHistory(Map<String, List<VisitDetail>> details) throws BaseTBLeprosyVisitAction.ValidationException {

        TBLeprosyMedicalHistoryActionHelper actionHelper = new TBLeprosyMedicalHistory(mContext, memberObject);
        BaseTBLeprosyVisitAction action = getBuilder(context.getString(R.string.skeleton_medical_history))
                .withOptional(true)
                .withDetails(details)
                .withHelper(actionHelper)
                .withFormName(Constants.SKELETON_FOLLOWUP_FORMS.MEDICAL_HISTORY)
                .build();
        actionList.put(context.getString(R.string.skeleton_medical_history), action);

    }

    private void evaluateSkeletonPhysicalExam(Map<String, List<VisitDetail>> details) throws BaseTBLeprosyVisitAction.ValidationException {

        TBLeprosyPhysicalExamActionHelper actionHelper = new TBLeprosyPhysicalExamActionHelper(mContext, memberObject);
        BaseTBLeprosyVisitAction action = getBuilder(context.getString(R.string.skeleton_physical_examination))
                .withOptional(true)
                .withDetails(details)
                .withHelper(actionHelper)
                .withFormName(Constants.SKELETON_FOLLOWUP_FORMS.PHYSICAL_EXAMINATION)
                .build();
        actionList.put(context.getString(R.string.skeleton_physical_examination), action);
    }

    private void evaluateSkeletonHTS(Map<String, List<VisitDetail>> details) throws BaseTBLeprosyVisitAction.ValidationException {

        TBLeprosyActionHelper actionHelper = new TBLeprosyActionHelper(mContext, memberObject);
        BaseTBLeprosyVisitAction action = getBuilder(context.getString(R.string.skeleton_hts))
                .withOptional(true)
                .withDetails(details)
                .withHelper(actionHelper)
                .withFormName(Constants.SKELETON_FOLLOWUP_FORMS.HTS)
                .build();
        actionList.put(context.getString(R.string.skeleton_hts), action);
    }

    @Override
    protected String getEncounterType() {
        return Constants.EVENT_TYPE.SKELETON_SERVICES;
    }

    @Override
    protected String getTableName() {
        return Constants.TABLES.SKELETON_SERVICE;
    }

    private class TBLeprosyMedicalHistory extends TBLeprosyMedicalHistoryActionHelper {


        public TBLeprosyMedicalHistory(Context context, MemberObject memberObject) {
            super(context, memberObject);
        }

        @Override
        public String postProcess(String s) {
            if (StringUtils.isNotBlank(medical_history)) {
                try {
                    evaluateSkeletonPhysicalExam(details);
                    evaluateSkeletonHTS(details);
                } catch (BaseTBLeprosyVisitAction.ValidationException e) {
                    e.printStackTrace();
                }
            }
            new AppExecutors().mainThread().execute(() -> callBack.preloadActions(actionList));
            return super.postProcess(s);
        }

    }

    private class TBLeprosyPhysicalExamActionHelper extends org.smartregister.chw.tbleprosy.actionhelper.TBLeprosyPhysicalExamActionHelper {

        public TBLeprosyPhysicalExamActionHelper(Context context, MemberObject memberObject) {
            super(context, memberObject);
        }

        @Override
        public String postProcess(String s) {
            if (StringUtils.isNotBlank(medical_history)) {
                try {
                    evaluateSkeletonHTS(details);
                } catch (BaseTBLeprosyVisitAction.ValidationException e) {
                    e.printStackTrace();
                }
            }
            new AppExecutors().mainThread().execute(() -> callBack.preloadActions(actionList));
            return super.postProcess(s);
        }

    }

}
