package org.example.template;

public class HtmlTemplateLoader {

  public static String loadHtmlTemplate() {

    String htmlTemplate = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <title>Student Report</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div id=\"output\">\n" +
            "</div>\n" +
            "\n" +
            "<script>\n" +
            "    var jsonData = <!-- JSON_DATA -->;\n" +
            "\n" +
            "    function formatStudentData(data) {\n" +
            "        var outputDiv = document.getElementById(\"output\");\n" +
            "\n" +
            "        data.forEach(function (student, index) {\n" +
            "            var studentDiv = document.createElement(\"div\");\n" +
            "            studentDiv.innerHTML = student.studentName + \", Total Credit \" + student.totalCredits;\n" +
            "\n" +
            "            student.courseNames.forEach(function (course, i) {\n" +
            "                var courseDiv = document.createElement(\"div\");\n" +
            "                courseDiv.innerHTML = course + \", Total Time: \" + student.totalTimes[i] + \", Credit: \" + student.courseCredits[i] + \", Instructor Name: \" + student.instructorNames[i];\n" +
            "                studentDiv.appendChild(courseDiv);\n" +
            "            });\n" +
            "\n" +
            "            outputDiv.appendChild(studentDiv);\n" +
            "            if (index < data.length - 1) {\n" +
            "                var lineBreak = document.createElement(\"hr\");\n" +
            "                outputDiv.appendChild(lineBreak);\n" +
            "            }\n" +
            "        });\n" +
            "    }\n" +
            "\n" +
            "    formatStudentData(jsonData);\n" +
            "</script>\n" +
            "</body>\n" +
            "</html>";


    return htmlTemplate;
  }
}
