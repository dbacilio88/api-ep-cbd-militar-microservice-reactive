create or replace  function t_func_update_militar()
   returns trigger
   language plpgsql
as $$
declare militar_dni varchar(8);
begin
	if
		new.cip <> old.cip
		or new.grade_id <> old.grade_id
		or new.specialty_id <> old.specialty_id
		or new.person_id <> old.person_id
	then
		insert into militar_audits (militar_id,changed_on,cip,specialty_id) values (old.militar_id,now(),old.cip, old.specialty_id);
	end if;
return new;
end;
$$

create or replace trigger t_update_militar
after update
on militar
for each row
execute procedure t_func_update_militar();

select * from militar_audits;

create table militar_audits (
   id int generated always as identity,
   militar_id int not null,
   changed_on timestamp(6) not null,
   cip varchar(9) not null,
   specialty_id int not null
);

insert into public.grade (grade_id,description,"name") values
	 (1,'general de ejército','gral ej'),
	 (2,'general de división','gral div'),
	 (3,'general de brigada','gral brig'),
	 (4,'coronel','crl'),
	 (5,'teniente coronel','tte crl'),
	 (6,'mayor','my'),
	 (7,'capitán','cap'),
	 (8,'teniente','tte'),
	 (9,'sub teniente','stte'),
	 (10,'técnico jefe supefior','tco js');

insert into public.grade (grade_id,description,"name") values
	 (11,'técnico jefe','tco j'),
	 (12,'técnico primero','tco 1'),
	 (13,'técnico segundo','tco 2'),
	 (14,'técnico tercero','tco 3'),
	 (15,'suboficial primero','so 1'),
	 (16,'suboficial segundo','so 2'),
	 (17,'suboficial tercero','so 3');

insert into public.person (person_id,document_number,last_name,"name") values
	 (1,'44910167','bacilio de la cruz','christian david'),
	 (2,'44910168','bacilio de la cruz	christian david','christian david');

insert into public.specialty (specialty_id,description,"name") values
	 (1,'infantería','inf'),
	 (2,'caballería','cab');
