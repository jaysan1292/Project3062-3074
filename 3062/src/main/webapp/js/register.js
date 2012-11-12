$(document).ready(function () {
    // Set up register form popovers
    $('#studentNumber')
        .attr('data-title', 'Student Number')
        .attr('data-trigger', 'manual')
        .attr('data-placement', 'right')
        .attr('data-content', 'This is your student number, which should be exactly nine numerical digits.<br/><br/><table class="table"><tr><td><i class="icon-ok"></i> Correct</td><td><i class="icon-remove"></i> Incorrect</td></tr><tr><td>100789101<br/>100147145<br/>854213447</td><td>1001234567<br/>100abc125<br/>452.334.168</td></tr></table>');

    $('#studentNumber').focus(function () {$('#studentNumber').popover('show')});
    $('#studentNumber').blur(function () {$('#studentNumber').popover('hide')});

    $('#password, #password-confirm')
        .attr('data-title', 'Password')
        .attr('data-trigger', 'manual')
        .attr('data-placement', 'right')
        .attr('data-content', 'There aren\'t many restrictions on your password, but it must be at least 6 characters.');

    $('#password').focus(function () {$('#password').popover('show')});
    $('#password').blur(function () {$('#password').popover('hide')});
    $('#password-confirm').focus(function () {$('#password-confirm').popover('show')});
    $('#password-confirm').blur(function () {$('#password-confirm').popover('hide')});
});

$(document).ready(function () {
    // disable the signup form as it isn't quite ready yet :p
    $('#register-form').find('input, select, button').each(function () {$(this).attr('disabled', '')});
//    $('#register-form').validate({
//        debug: true,
//        rules: {
//            firstName:     'required',
//            lastName:      'required',
//            studentNumber: {
//                required:  true,
//                minlength: 9,
//                maxlength: 9
//            },
//            messages:      {
//                firstName:     'Please specify your first name.',
//                lastName:      'Please specify your last name.',
//                studentNumber: 'Please specify your GBC student ID, as it is required to use this site.'
//            }
//        }
//    });
//    $('#register-form').submit(function (e) {
//        e.preventDefault();
//        console.log('Performing validation of register form.');
//        const invalidPassword = '###INVALID###';
//
//        var password, password2;
//
//        password = $('#password').val();
//        password2 = $('#password-confirm').val();
//
//        var user = {
//            firstName: $('#firstName').val(),
//            lastName:  $('#lastName').val(),
//            studentId: $('#studentNumber').val(),
//            program:   $('#program').val(),
//            password:  ''
//        };
//
//        var firstNameValid, lastNameValid,
//            studentIdValid, programValid,
//            passwordValid;
//
//        firstNameValid = validateName(user.firstName);
//        lastNameValid = validateName(user.lastName);
//        studentIdValid = validateStudentNumber(user.studentId);
//        programValid = validateProgram(user.program);
//        passwordValid = validatePassword($('#password').val(), $('#password-confirm').val());
//
//        var formIsValid =
//            firstNameValid && lastNameValid &&
//                studentIdValid && programValid &&
//                passwordValid;
//
//        if (formIsValid) {
//            user.password = $('#password').val();
//            console.log('Form is valid! Submitting to server...');
//
////            $.post({
////                url:     getRegisterUrl(),
////                data:    $(user).toJSON(),
////                success: function (data, textStatus, jqXHR) {
////                    console.log(data);
////                    console.log(textStatus);
////                    console.log(jqXHR);
////                }
////            });
//        } else {
//            console.log('Form has problems D:');
//        }
//    });
})
;

function validateName(name) {
    return /\S/.test(name);
}

function validateStudentNumber(id) {
    return (/^([0-9]){9}$/.test(id)) && (id.length == 9);
}

function validateProgram(program) {
    return program != 'invalid';
}

function validatePassword(password, check) {
    return (password == check) && (password.length >= 6);
}
