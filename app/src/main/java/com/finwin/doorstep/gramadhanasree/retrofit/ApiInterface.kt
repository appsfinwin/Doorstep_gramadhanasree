package com.finwin.doorstep.gramadhanasree.retrofit

import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details.pojo.GetLoanPeriodResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo.CreateJlGLoanResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo.GetGroupSelectResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo.GetJlgProductResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists.pojo.JlgPendingListResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists.pojo.RemoveAccountResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group.pojo.SearchGroupAccountResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.search_center.pojo.GetSearchCenterResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.pojo.GetCenterByBranchResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.pojo.GetSearchGroupResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.search_member.pojo.GetSearchMemberResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.CodeMasterResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.GroupAccountDetails
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.SplitTransactionResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.pojo.LoanCollectionResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.pojo.GetLoanNumbers
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.pojo.GetSchemes
import com.finwin.doorstep.gramadhanasree.home.agent_management.change_password.pojo.ChangePasswordResponse
import com.finwin.doorstep.gramadhanasree.home.bc_report.daily_report.pojo.BcReportResponse
import com.finwin.doorstep.gramadhanasree.home.customer_creation.pojo.*
import com.finwin.doorstep.gramadhanasree.home.enquiry.balance_enquiry.pojo.BalanceEnquiryResponse
import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.pojo.MiniStatementResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.pojo.JlgCreateCenterResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.pojo.getjLgCenterResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.pojo.CreateGroupResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.pojo.CashDepositResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.pojo.GetAccountHolderResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal.pojo.CashWithdrawalResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal.pojo.OtpResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.pojo.GetLoanAccountHolderResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.SearchResponse
import com.finwin.doorstep.gramadhanasree.login.pojo.LoginResponse


import com.finwin.doorstep.gramadhanasree.login.pojo.getMasters.GetMasters
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @POST("userLogin")
    fun login(@Body body: RequestBody?): Observable<LoginResponse>

    @POST("getCustUnderAgent")
    fun getSearchData(@Body body: RequestBody?): Observable<SearchResponse>

    @POST("getMastersAll")
    fun getMasters(): Observable<GetMasters>

    @POST("getAccountHolder")
    fun getAccountHolder(@Body body: RequestBody?): Observable<GetAccountHolderResponse>

    @POST("cashDeposit")
    fun cashDeposit(@Body body: RequestBody?): Observable<CashDepositResponse>

    @POST("OTPGenerate")
    fun generateOtp(@Body body: RequestBody?): Observable<OtpResponse>

    @POST("CashWithdrawal")
    fun cashWithdrawal(@Body body: RequestBody?): Observable<CashWithdrawalResponse>

    @POST("getMiniStatement")
    fun miniStatement(@Body body: RequestBody?): Observable<MiniStatementResponse>

    @POST("balanceEnqury")
    fun balanceEnqury(@Body body: RequestBody?): Observable<BalanceEnquiryResponse>

    @POST("BCReport")
    fun bcReport(@Body body: RequestBody?): Observable<BcReportResponse>

    @POST("passwordChange")
    fun changePassword(@Body body: RequestBody?): Observable<ChangePasswordResponse>

    @POST("GetAllLoanCentreByBranch")
    fun getJlgBranches(@Body body: RequestBody?): Observable<getjLgCenterResponse>

    @POST("LoanCentreCreator")
    fun jlgCreateCenter(@Body body: RequestBody?): Observable<JlgCreateCenterResponse>

    @POST("LoanCentreUpdater")
    fun jlgUpdateCenter(@Body body: RequestBody?): Observable<JlgCreateCenterResponse>

    @POST("JLGFetchAccNo")
    fun getGroupSearch(@Body body: RequestBody?): Observable<SearchGroupAccountResponse>

    @GET("RefCodeMaster")
    fun getCodeMasters(): Observable<CodeMasterResponse>

    @POST("JLGTransGroupSelect")
    fun groupAccountDetails(@Body body: RequestBody?): Observable<GroupAccountDetails>

    @POST("GetRefCodes_Api")
    fun getReferenceCodes(@Body body: RequestBody?): Observable<ReferenceCodesResponse>

    @GET("{pincode}")
    fun getPostOffice(@Path("pincode") ifsc: String?): Single<getPostOfficeResponse?>?

    @POST("CustomerManager_Api")
    fun customerCreation(@Body body: RequestBody?): Observable<CustomerCreationResponse>

    @POST("OTPCreation")
    fun createOtp(@Body body: RequestBody?): Single<CreateOtpResponse?>?

    @POST("OTPCreation")
    fun validateOtp(@Body body: RequestBody?): Single<ValidateOtpResponse?>?

    @POST("getAccountHolder")
    suspend fun getAccount(@Body body: RequestBody?): GetAccountHolderResponse

    @POST("getLoanCustUnderAgent")
    fun getSearchLoanNumbers(@Body body: RequestBody?): Single<GetLoanNumbers?>?

    @POST("getLoanScheme")
    fun getLoanScheme(@Body body: RequestBody?): Single<GetSchemes?>?

    @POST("LoanCentreSearch")
    fun getsearchCenter(@Body body: RequestBody?): Single<GetSearchCenterResponse?>?

    @POST("getCustUnderAgentbyCustId")
    fun getSearchMember(@Body body: RequestBody?): Single<GetSearchMemberResponse?>?

    @POST("JLGLoanGroup")
    fun createGroup(@Body body: RequestBody?): Single<CreateGroupResponse?>?

    @POST("JLGGroupSelect")
    fun getGroup(@Body body: RequestBody?): Single<GetGroupSelectResponse?>?

    @POST("SelectGroup")
    fun getSearchGroup(@Body body: RequestBody?): Single<GetSearchGroupResponse?>?

    @POST("GetAllLoanCentreByBranch")
    fun getCenterByBranch(@Body body: RequestBody?): Single<GetCenterByBranchResponse?>?

    @POST("JLGLoanPeriod")
    fun getJlgLoanPeriod(@Body body: RequestBody?): Single<GetLoanPeriodResponse?>?

    @POST("JLGSelectProduct")
    fun getJlgProducts(@Body body: RequestBody?): Single<GetJlgProductResponse?>?

    @POST("LoanMaster")
    fun createJlgLoan(@Body body: RequestBody?): Single<CreateJlGLoanResponse?>?

    @POST("JLGLoanTransaction")
    fun jlgSplitTransaction(@Body body: RequestBody?): Single<SplitTransactionResponse?>?

    @POST("JLGPending")
    fun getPendingList(@Body body: RequestBody?): Single<JlgPendingListResponse?>?

    @POST("JLGLoanRemove")
    fun removeAccount(@Body body: RequestBody?): Single<RemoveAccountResponse?>?

    @POST("getLoanAccountHolder")
    fun getLoanAccountHolder(@Body body: RequestBody?): Single<GetLoanAccountHolderResponse?>?

    @POST("LoanReceiptCash")
    fun loanCollection(@Body body: RequestBody?): Single<LoanCollectionResponse?>?


//    @POST("getLoanAccountHolder")
//    fun getLoanAccountHolder(@Body body: RequestBody?): Single<GetLoanAccountHolderResponse?>?

//    @POST("getLoanCustUnderAgent")
//    suspend fun getSearchLoanNumbers(@Body body: RequestBody?) : GetLoanNumbers

}