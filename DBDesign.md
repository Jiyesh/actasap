## Introduction ##

Database Design.


## Details ##

### Tables ###

Accounts
  * Account ID
  * Account Name
  * Segment1 `signifies what account type it is: Asset (01) or Liability (02) or Owners Equity (03)`
  * Segment2
  * Description

Journals
  * Journal ID
  * Date
  * Amount
  * DR Account
  * CR Account
  * Description

### Relationships ###

Journal.DR Account = Account.Account ID

&lt;BR&gt;


Journal.CR Account = Account.Account ID

### Seeded Accounts ###
| **Name** | **Segment1** | **Segment2** |
|:---------|:-------------|:-------------|
|Cash      |01            |01            |
|Capital   |03            |01            |
|Notes payable|02            |01            |
|Prepaid rent|01            |02            |
|Equipment |01            |03            |
|Accounts receivable|01            |04            |
|Service revenue|03            |02            |
|Unearned revenue|02            |02            |
|Salary expense|03            |03            |
|Utility expense|03            |04            |
|Inventory |01            |05            |
|Sales revenue|03            |05            |
|Interest payable|02            |03            |
|Rent expense|03            |06            |
|Depreciation expense|03            |07            |
|Accounts payable|02            |03            |
|Utilites payable|03            |08            |
|Cost of Goods|03            |09            |
|Interest expense|03            |10            |