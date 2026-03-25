import { useState, useEffect } from "react";

const App = () => {
  const [employees, setEmployees] = useState([]);
  const [filters, setFilters] = useState({
    firstName: "",
    lastName: "",
    department: "",
  });
  const [page, setPage] = useState(0);

  const fetchEmployees = (resetFilters) => {
    let query = `http://localhost:8080/api/employees?page=${page}&size=5`;

    if (!resetFilters) {
      if (filters.firstName) {
        query += `&firstName=${filters.firstName}`;
      }
      if (filters.lastName) {
        query += `&lastName=${filters.lastName}`;
      }
      if (filters.department) {
        query += `&department=${filters.department}`;
      }
    }

    fetch(query)
      .then((res) => res.json())
      .then((data) => {
        setEmployees(data.content);
      });
  };

  useEffect(() => {
    fetchEmployees();
    return () => {
      setEmployees([]);
    };
  }, [page]);

  const resetFilters = () => {
    setFilters({ firstName: "", lastName: "", department: "" });
    fetchEmployees(true);
  };

  console.log(filters);

  return (
    <div>
      <h1>Employee List</h1>

      {/* Search Filters */}
      <div>
        <input
          type="search"
          placeholder="First Name"
          value={filters.firstName || ""}
          onChange={(e) => {
            setFilters({ ...filters, firstName: e.target.value });
          }}
        />
        <input
          type="search"
          placeholder="Last Name"
          value={filters.lastName || ""}
          onChange={(e) => {
            setFilters({ ...filters, lastName: e.target.value });
          }}
        />
        <input
          type="search"
          placeholder="Department"
          value={filters.department || ""}
          onChange={(e) => {
            setFilters({ ...filters, department: e.target.value });
          }}
        />
        <button onClick={() => fetchEmployees()}>Search</button>
        <button onClick={resetFilters}>Reset</button>
      </div>

      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Department</th>
          </tr>
        </thead>
        <tbody>
          {employees.map((employee) => {
            return (
              <tr key={employee.id}>
                <td>{employee.id}</td>
                <td>{employee.firstName}</td>
                <td>{employee.lastName}</td>
                <td>{employee.email}</td>
                <td>{employee.department}</td>
              </tr>
            );
          })}
        </tbody>
      </table>
      <div>
        <button disabled={page === 0} onClick={() => setPage(page - 1)}>
          Previous
        </button>
        <button onClick={() => setPage(page + 1)}>Next</button>
      </div>
    </div>
  );
};

export default App;
