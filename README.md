Limitations while doing this project

* This project is developed using windows machine and cannot test ios part of the app so I didn't fix any ios issues (ie platform module)
* Due to time constraint, only use cases were tested. Use cases in this project are mostly `glue code` and mostly are expected to return a success result only so unit tests looks a bit ridiculous.
* Search Api returns more than the input limit so the app filters the excess.
* Search Api sometimes returns items with null `kind` which is required based on the instructions so this items are filtered out.
* Search Api only has the parameter `limit` and therefore cannot implement a paginated approach. The workaround is increasing the limit every call.
