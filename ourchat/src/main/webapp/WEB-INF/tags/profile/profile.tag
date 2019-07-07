<%@tag description="Tag describing the profile" pageEncoding="UTF-8"%>

<%@attribute name="profile" required="true" type="app.ourchat.profile.model.Profile" description="profile"%>

<h2>${profile.name}</h2>
<button class="js-edit-profile-button" data-tab="edit" data-user-id="${profile.userId}">Edit</button>
<button class="js-delete-profile-button" data-user-id="${profile.userId}">Delete</button>

<script>
    $(".js-edit-profile-button").on("click", function () {
        if (exists($(this).data("id")))
            $("#js-profile-content").load("/ajaxPages/profile/" + $(this).data("tab") + ".jsp", {"userId": $(this).data("user-id")});
    });
</script>