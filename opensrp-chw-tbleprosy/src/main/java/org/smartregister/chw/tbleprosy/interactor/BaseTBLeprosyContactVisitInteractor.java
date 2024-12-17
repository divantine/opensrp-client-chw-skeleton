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

public class BaseTBLeprosyContactVisitInteractor extends BaseTBLeprosyVisitInteractor {

    protected BaseTBLeprosyVisitContract.InteractorCallBack callBack;

    String visitType;
    private final TBLeprosyLibrary tbleprosyLibrary;
    private final LinkedHashMap<String, BaseTBLeprosyVisitAction> actionList;
    protected AppExecutors appExecutors;
    private ECSyncHelper syncHelper;
    private Context mContext;


    @VisibleForTesting
    public BaseTBLeprosyContactVisitInteractor(AppExecutors appExecutors, TBLeprosyLibrary TBLeprosyLibrary, ECSyncHelper syncHelper) {
        this.appExecutors = appExecutors;
        this.tbleprosyLibrary = TBLeprosyLibrary;
        this.syncHelper = syncHelper;
        this.actionList = new LinkedHashMap<>();
    }

    public BaseTBLeprosyContactVisitInteractor(String visitType) {
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
                evaluatetbleprosyNjiaUchunguziKifuaKikuu(details);
                evaluatetbleprosyUchunguziKifuaKikuu(details);
                evaluatetbleprosyKuchukuaSampuli(details);


            } catch (BaseTBLeprosyVisitAction.ValidationException e) {
                Timber.e(e);
            }

            appExecutors.mainThread().execute(() -> callBack.preloadActions(actionList));
        };

        appExecutors.diskIO().execute(runnable);
    }

    private void evaluatetbleprosyNjiaUchunguziKifuaKikuu(Map<String, List<VisitDetail>> details) throws BaseTBLeprosyVisitAction.ValidationException {

        TBLeprosyMedicalHistoryActionHelper actionHelper = new TBLeprosyMedicalHistory(mContext, memberObject);
        BaseTBLeprosyVisitAction action = getBuilder(context.getString(R.string.tbleprosy_njia_uchunguzi_kifua_kikuu))
                .withOptional(true)
                .withDetails(details)
                .withHelper(actionHelper)
                .withFormName(Constants.tbleprosy_FOLLOWUP_FORMS.UCHUNGUZI_KIFUA_KIKUU)
                .build();
        actionList.put("Njia ya Uchunguzi Kifua Kikuu", action);

    }

    private void evaluatetbleprosyUchunguziKifuaKikuu(Map<String, List<VisitDetail>> details) throws BaseTBLeprosyVisitAction.ValidationException {

        TBLeprosyPhysicalExamActionHelper actionHelper = new TBLeprosyPhysicalExamActionHelper(mContext, memberObject);
        BaseTBLeprosyVisitAction action = getBuilder(context.getString(R.string.tbleprosy_uchunguzi_kifua_kikuu))
                .withOptional(true)
                .withDetails(details)
                .withHelper(actionHelper)
                .withFormName(Constants.tbleprosy_FOLLOWUP_FORMS.UCHUNGUZI_KIFUA)
                .build();
        actionList.put(context.getString(R.string.tbleprosy_uchunguzi_kifua_kikuu), action);
    }

    private void evaluatetbleprosyKuchukuaSampuli(Map<String, List<VisitDetail>> details) throws BaseTBLeprosyVisitAction.ValidationException {

        TBLeprosyActionHelper actionHelper = new TBLeprosyActionHelper(mContext, memberObject);
        BaseTBLeprosyVisitAction action = getBuilder(context.getString(R.string.tbleprosy_kuchukua_sampuli))
                .withOptional(true)
                .withDetails(details)
                .withHelper(actionHelper)
                .withFormName(Constants.tbleprosy_FOLLOWUP_FORMS.KUCHUKUA_SAMPULI)
                .build();
        actionList.put(context.getString(R.string.tbleprosy_kuchukua_sampuli), action);
    }

    @Override
    protected String getEncounterType() {
        return Constants.EVENT_TYPE.tbleprosy_SERVICES;
    }

    @Override
    protected String getTableName() {
        return Constants.TABLES.tbleprosy_SERVICE;
    }

    private class TBLeprosyMedicalHistory extends TBLeprosyMedicalHistoryActionHelper {


        public TBLeprosyMedicalHistory(Context context, MemberObject memberObject) {
            super(context, memberObject);
        }

        @Override
        public String postProcess(String s) {
//            if (StringUtils.isNotBlank(medical_history)) {
//                try {
//                    evaluatetbleprosyPhysicalExam(details);
//                    evaluatetbleprosyHTS(details);
//                } catch (BaseTBLeprosyVisitAction.ValidationException e) {
//                    e.printStackTrace();
//                }
//            }
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
//            if (StringUtils.isNotBlank(medical_history)) {
//                try {
//                    evaluatetbleprosyHTS(details);
//                } catch (BaseTBLeprosyVisitAction.ValidationException e) {
//                    e.printStackTrace();
//                }
//            }
            new AppExecutors().mainThread().execute(() -> callBack.preloadActions(actionList));
            return super.postProcess(s);
        }

    }

}
