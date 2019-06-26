function ajaxMake(url, params) {
    $.ajax({
        url: url,
        data: params,
        type: "put",
        beforeSend: function (xhr) {
            console.log("before make");
        },
        complete: function (jqXHR, textStatus) {
            console.log("make finished");
        },
        success: function (data, textStatus, jqXHR) {
            console.log("make success");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("make error");
        }
    });
}

function ajaxEdit(url, params) {
    $.ajax({
        url: url,
        data: params,
        type: "post",
        beforeSend: function (xhr) {
            console.log("before edit");
        },
        complete: function (jqXHR, textStatus) {
            console.log("edit finished");
        },
        success: function (data, textStatus, jqXHR) {
            console.log("edit success");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("edit error");
        }
    });
}

function ajaxRemove(url, params) {
    $.ajax({
        url: url,
        data: params,
        type: "put",
        beforeSend: function (xhr) {
            console.log("before remove");
        },
        complete: function (jqXHR, textStatus) {
            console.log("remove finished");
        },
        success: function (data, textStatus, jqXHR) {
            console.log("remove success");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("remove error");
        }
    });
}

function ajaxView(url, params) {
    $.ajax({
        url: url,
        data: params,
        type: "get",
        beforeSend: function (xhr) {
            console.log("before view");
        },
        complete: function (jqXHR, textStatus) {
            console.log("view finished");
        },
        success: function (data, textStatus, jqXHR) {
            console.log("view success");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("view error");
        }
    });
}
