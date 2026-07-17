<%@ page contentType="text/html;charset=UTF-8" %>
</div><!-- end page-content -->
<footer class="footer">
  <p>&copy; <%= java.time.Year.now().getValue() %> ${not empty config ? config.instituteName : 'Coaching Management System'}</p>
</footer>
</body>
</html>
