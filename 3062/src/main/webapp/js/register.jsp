<%@ page contentType="text/javascript" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

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
            $('#register-form').validate({
                onkeyup:  false,
                rules:    {
                    firstName:          'required',
                    lastName:           'required',
                    password:           {
                        required:  true,
                        minlength: 6
                    },
                    'password-confirm': {
                        required: true,
                        equalTo:  '#password'
                    },
                    studentNumber:      {
                        required:  true,
                        minlength: 9,
                        maxlength: 9,
                        digits:    true,
                        remote:    '<c:url value="/register"/>'
                    },
                    email:              {
                        required: true,
                        email:    true,
                        remote:   '<c:url value="/register"/>'
                    },
                    program:            {
                        required: true
                    }
                },
                messages: {
                    firstName:          'Please specify your first name.',
                    lastName:           'Please specify your last name.',
                    'password-confirm': {
                        equalTo:   'Both passwords must match.',
                        minlength: 'Password must be at least six (6) characters.'
                    },
                    studentNumber:      {
                        required:  'Please specify your GBC student ID, as it is required to use this site.',
                        minlength: 'Student number must be exactly nine (9) characters.',
                        maxlength: 'Student number must be exactly nine (9) characters.',
                        remote:    'Sorry, that student number isn\'t valid.'
                    },
                    email:              {
                        required: 'Email is required.',
                        remote:   'Sorry, it looks like that email is already taken.'
                    },
                    program:            {
                        required: 'You must specify the program you are enrolled in.'
                    }
                }
            });
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
