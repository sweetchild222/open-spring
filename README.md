This software is built using Java Spring and serves as the backend for a blog service where anyone can freely post diaries, articles, knowledge, and more. you can check the running software here.

[https://leafstory.click](https://leafstory.click)

the software serving as the frontend for this blog service can be found here.

[https://github.com/sweetchild222/open-react](https://github.com/sweetchild222/open-react)

The features of this software are as follows

* Neither JPA nor MyBatis is used. the database is accessed directly using SQL.
* It supports MariaDB. MariaDB configuration and table creation query can be found here. [https://github.com/sweetchild222/open-spring/tree/main/reference/mariadb](https://github.com/sweetchild222/open-spring/tree/main/reference/mariadb)
* It saves and serves uploaded images in various sizes.
* Logs the status of the running program.
* It provides MDXEditor as markdown editor. [https://mdxeditor.dev](https://mdxeditor.dev)
* The provided API follows a design that is as RESTful as possible.

The API specification follows below.



<details>
  <summary>Authenticate API</summary>
    <table style="border-collapse: collapse;">        
        <thead style="background-color:gray; color:lightGray;">
            <tr>
                <th style="border: 1px solid">Request</th>
                <th style="border: 1px solid">Response</th>
                <th style="border: 1px solid">Access constraint</th>
            </tr>
        </thead>
        <tbody style="white-space: pre; color: white;">
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/authenticate<br><br>{<br>&emsp;username: 'rabbit@google.com',<br>&emsp;password: '...'<br>}</td>
                <td style="border: 1px solid">{<br>&emsp;<span title="authenticated user_id"><ins>user_id: 217,</ins></span><br>&emsp;<span title="authenticated blog_id"><ins>blog_id: 36,</ins></span><br>&emsp;<span title="append 'Authorization: Bearer {jwt}' to request header when access the api required authorization"><ins>jwt: '...'</span></ins><br>}</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
        </tbody>
    </table>
</details>


<details>
  <summary>User API</summary>
    <table style="border-collapse: collapse;">        
        <thead style="background-color:gray; color:lightGray;">
            <tr>
                <th style="border: 1px solid">Request</th>
                <th style="border: 1px solid">Response</th>
                <th style="border: 1px solid">Access constraint</th>
            </tr>
        </thead>
        <tbody style="white-space: pre; color: white;">
            <tr>
                <td style="border: 1px solid"><i>Get</i> api/user/:userId</td>
                <td style="border: 1px solid">{<br>&emsp;image: 'https://a.webp',<br>&emsp;blog_id: 36,<br>&emsp;nickname: 'professional',<br>&emsp;id: 217,<br>&emsp;create_at: 1785394653000,<br>&emsp;username: 'abc1234@google.com'<br>}</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Get</i> api/user?<br>id=5,3</td>
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;image: 'https://a.webp',<br>&emsp;&emsp;blog_id: 36,<br>&emsp;&emsp;nickname: 'professional',<br>&emsp;&emsp;id: 217,<br>&emsp;&emsp;<span title="or ADMIN"><ins>role: 'USER'</ins></span><br>&emsp;}<br>]</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Get</i> api/:username/exist</td>
                <td style="border: 1px solid">{<br>&emsp;exist:1<br>}</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/user<br><br>{<br>&emsp;username: 'abc123@google.com',<br>&emsp;password: '...',<br>&emsp;image: 'https://abc.webp',<br>&emsp;nickname: 'angel'<br>}</td>
                <td style="border: 1px solid">{<br>&emsp;<span title="created user id"><ins>id: 99</ins></span><br>}</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Patch</i> api/user/:userId<br><br>{<br>&emsp;<span title="default is false. to user withdraw, set true"><ins>withdraw: false,</ins></span><br>&emsp;username: 'rabbit@google.com',<br>&emsp;image: 'https://abc.webp',<br>&emsp;password: '....',<br>&emsp;nickname: 'rabbit',<br>&emsp;role:'ADMIN'<br>}</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the userId <br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/:userId/password<br><br>{<br>&emsp;password: '...',<br>}</td>
                <td style="border: 1px solid">{<br>&emsp;correct: true<br>}</td>
                <td style="border: 1px solid">the userId <br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> certify/user-join<br><br>{<br>&emsp;email: 'rabbit@google.com'<br>}</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Patch</i> certify/user-join<br><br>{<br>&emsp;email: 'rabbit@google.com',<br>&emsp;code:'XXXXXX'<br>}</td>
                <td style="border: 1px solid">{<br>&emsp;match: true<br>}</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> certify/password-reset<br><br>{<br>&emsp;email: 'rabbit@google.com'<br>}</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Patch</i> certify/password-reset<br><br>{<br>&emsp;email: 'rabbit@google.com',<br>&emsp;code:'XXXXXX'<br>}</td>
                <td style="border: 1px solid">{<br>&emsp;match: true<br>}</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Patch</i> password-reset/email/:email</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">permit all</td>
            </tr>
        </tbody>
    </table>
</details>


<details>
  <summary>Article API</summary>
    <table style="border-collapse: collapse;">
        <thead style="background-color:gray; color:lightGray;">
            <tr>
                <th style="border: 1px solid;">Request</th>
                <th style="border: 1px solid;">Response</th>
                <th style="border: 1px solid;">Access constraint</th>
            </tr>
        </thead>
        <tbody style="color: white;">
            <tr>
                <td style="border: 1px solid;"><i>Get</i> api/article?<br>offset=10&limit=5&<br><span title='or like_count, comment_count'><ins>order_type=post_at</ins></span>&order=0&<br>id=3,5,6&blog_id=15,20&<br><span title="to search in content or title"><ins>keyword='good'</ins></span></td>
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;id: 5,<br>&emsp;&emsp;title: 'this is title',<br>&emsp;&emsp;thumbnail: 'https://a.webp',<br>&emsp;&emsp;blog_id: 39,<br>&emsp;&emsp;bookmark_count: 1,<br>&emsp;&emsp;<span title='0 means in progress'><ins>posted: 1,</ins></span><br>&emsp;&emsp;<spa title="the article's content head"><ins>head: 'this is ..',</ins></span><br>&emsp;&emsp;category_id: 210,<br>&emsp;&emsp;user_id: 34,<br>&emsp;&emsp;showed: 120,<br>&emsp;&emsp;update_at: 1789363211000,<br>&emsp;&emsp;<span title="original article id of modifying posted article"><ins>source_id: null,</ins></span><br>&emsp;&emsp;post_at: 1788495370000,<br>&emsp;&emsp;create_at: 1788495330000,<br>&emsp;&emsp;comment_count: 1,<br>&emsp;&emsp;dislike_count: 3,<br>&emsp;&emsp;like_count: 9<br>&emsp;}<br>]</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/article<br><br>{<br>&emsp;title: 'this is title',<br>&emsp;content: 'this is content',<br>&emsp;<span title="the article's content head"><ins>head: 'this is head',</ins><span><br>&emsp;<span title="0 means in progress"><ins>posted: 1,</ins></span><br>&emsp;thumbnail: 'https://b.webp',<br>&emsp;category_id: 10,<br>&emsp;<span title="original article id of modifying posted article or null"><ins>souruce_id: 55</span></ins><br>}</td>
                <td style="border: 1px solid">{<br>&emsp;<span title="created article id"><ins>id: 343</ins></span><br>}</td>
                <td style="border: 1px solid">the category's blog_id<br>and<br>authenticated blog_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Put</i> api/article/:articleId<br><br>{<br>&emsp;title: 'this is title',<br>&emsp;content: 'this is content',<br>&emsp;head: 'this is head',<br>&emsp;posted: 1,<br>&emsp;thumbnail: 'https://a.webp',<br>&emsp;category_id: 10<br>}</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the category's blog_id<br>and<br>authenticated blog_id<br>must be equal</td>
            </tr>
            <tr>                
                <td style="border: 1px solid"><i>Delete</i> api/article/:articleId</td>                
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the article's blog_id<br>and<br>authenticated blog_id<br>must be equal</td>
            </tr>
            <tr>                
                <td style="border: 1px solid"><i>Get</i> api/blog/:blogId/article?<br>offset=10&limit=5&<br><span title="0 is ascending, 1 is descending by created time"><ins>order=0&</ins></span>category_id=5&<br>min_article_id=10&<br>max_article_id=30&<br><span title="0 means in progres, this parameter is available only when authenticated user_id and article's user_id are equal"><ins>posted=1</span></ins></td>                
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;id: 15,<br>&emsp;&emsp;title: 'this is title',<br>&emsp;&emsp;thumbnail: 'https://c.webp',<br>&emsp;&emsp;blog_id: 39,<br>&emsp;&emsp;bookmark_count: 1,<br>&emsp;&emsp;posted: 1,<br>&emsp;&emsp;head: 'this is ..',<br>&emsp;&emsp;category_id: 210,<br>&emsp;&emsp;user_id: 34,<br>&emsp;&emsp;showed: 120,<br>&emsp;&emsp;update_at: 1789363211000,<br>&emsp;&emsp;source_id: 36,<br>&emsp;&emsp;post_at: 1788495370000,<br>&emsp;&emsp;create_at: 1788495330000,<br>&emsp;&emsp;comment_count: 1,<br>&emsp;&emsp;dislike_count: 3,<br>&emsp;&emsp;like_count: 9<br>&emsp;}<br>]</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>                
                <td style="border: 1px solid"><i>Post</i> api/article/:articleId/showed</td>
                <td style="border: 1px solid"></td>                
                <td style="border: 1px solid">permit all</td>
            </tr>
        </tbody>
    </table>
</details>


<details>
  <summary>Article API</summary>
    <table style="border-collapse: collapse;">
        <thead style="background-color:gray; color:lightGray;">
            <tr>
                <th style="border: 1px solid;">Request</th>
                <th style="border: 1px solid;">Response</th>
                <th style="border: 1px solid;">Access constraint</th>
            </tr>
        </thead>
        <tbody style="color: white;">
            <tr>
                <td style="border: 1px solid;"><i>Get</i> api/article?<br>offset=10&limit=5&<br><span title='or like_count, comment_count'><ins>order_type=post_at</ins></span>&order=0&<br>id=3,5,6&blog_id=15,20&<br><span title="to search in content or title"><ins>keyword='good'</ins></span></td>
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;id: 5,<br>&emsp;&emsp;title: 'this is title',<br>&emsp;&emsp;thumbnail: 'https://a.webp',<br>&emsp;&emsp;blog_id: 39,<br>&emsp;&emsp;bookmark_count: 1,<br>&emsp;&emsp;<span title='0 means in progress'><ins>posted: 1,</ins></span><br>&emsp;&emsp;<spa title="the article's content head"><ins>head: 'this is ..',</ins></span><br>&emsp;&emsp;category_id: 210,<br>&emsp;&emsp;user_id: 34,<br>&emsp;&emsp;showed: 120,<br>&emsp;&emsp;update_at: 1789363211000,<br>&emsp;&emsp;<span title="original article id of modifying posted article"><ins>source_id: null,</ins></span><br>&emsp;&emsp;post_at: 1788495370000,<br>&emsp;&emsp;create_at: 1788495330000,<br>&emsp;&emsp;comment_count: 1,<br>&emsp;&emsp;dislike_count: 3,<br>&emsp;&emsp;like_count: 9<br>&emsp;}<br>]</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/article<br><br>{<br>&emsp;title: 'this is title',<br>&emsp;content: 'this is content',<br>&emsp;<span title="the article's content head"><ins>head: 'this is head',</ins><span><br>&emsp;<span title="0 means in progress"><ins>posted: 1,</ins></span><br>&emsp;thumbnail: 'https://b.webp',<br>&emsp;category_id: 10,<br>&emsp;<span title="original article id of modifying posted article or null"><ins>souruce_id: 55</span></ins><br>}</td>
                <td style="border: 1px solid">{<br>&emsp;<span title="created article id"><ins>id: 343</ins></span><br>}</td>
                <td style="border: 1px solid">the category's blog_id<br>and<br>authenticated blog_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Put</i> api/article/:articleId<br><br>{<br>&emsp;title: 'this is title',<br>&emsp;content: 'this is content',<br>&emsp;head: 'this is head',<br>&emsp;posted: 1,<br>&emsp;thumbnail: 'https://a.webp',<br>&emsp;category_id: 10<br>}</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the category's blog_id<br>and<br>authenticated blog_id<br>must be equal</td>
            </tr>
            <tr>                
                <td style="border: 1px solid"><i>Delete</i> api/article/:articleId</td>                
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the article's blog_id<br>and<br>authenticated blog_id<br>must be equal</td>
            </tr>
            <tr>                
                <td style="border: 1px solid"><i>Get</i> api/blog/:blogId/article?<br>offset=10&limit=5&<br><span title="0 is ascending, 1 is descending by created time"><ins>order=0&</ins></span>category_id=5&<br>min_article_id=10&<br>max_article_id=30&<br><span title="0 means in progres, this parameter is available only when authenticated user_id and article's user_id are equal"><ins>posted=1</span></ins></td>                
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;id: 15,<br>&emsp;&emsp;title: 'this is title',<br>&emsp;&emsp;thumbnail: 'https://c.webp',<br>&emsp;&emsp;blog_id: 39,<br>&emsp;&emsp;bookmark_count: 1,<br>&emsp;&emsp;posted: 1,<br>&emsp;&emsp;head: 'this is ..',<br>&emsp;&emsp;category_id: 210,<br>&emsp;&emsp;user_id: 34,<br>&emsp;&emsp;showed: 120,<br>&emsp;&emsp;update_at: 1789363211000,<br>&emsp;&emsp;source_id: 36,<br>&emsp;&emsp;post_at: 1788495370000,<br>&emsp;&emsp;create_at: 1788495330000,<br>&emsp;&emsp;comment_count: 1,<br>&emsp;&emsp;dislike_count: 3,<br>&emsp;&emsp;like_count: 9<br>&emsp;}<br>]</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>                
                <td style="border: 1px solid"><i>Post</i> api/article/:articleId/showed</td>
                <td style="border: 1px solid"></td>                
                <td style="border: 1px solid">permit all</td>
            </tr>
        </tbody>
    </table>
</details>


<details>
  <summary>Article Great API</summary>
    <table style="border-collapse: collapse;">        
        <thead style="background-color:gray; color:lightGray;">
            <tr>
                <th style="border: 1px solid">Request</th>
                <th style="border: 1px solid">Response</th>
                <th style="border: 1px solid">Access constraint</th>
            </tr>
        </thead>
        <tbody style="white-space: pre; color: white;">
            <tr>
                <td style="border: 1px solid"><i>Get</i> api/article/great?<br><span title="
essential prameter"><ins>user_id=217</span></ins>&<br><span title="
essential prameter"><ins>article_id=817</span></ins></td>
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;id:588,<br>&emsp;&emsp;article_id:817,<br>&emsp;&emsp;user_id:217,<br>&emsp;&emsp;great:1<br>&emsp;}<br>]</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/article/great<br><br>{<br>&emsp;user_id: 30,<br>&emsp;article_id: 70,<br>&emsp;<span title="-1 means disgreat, 1 means  great"><ins>great: 1</ins></span><br>}</td>
                <td style="border: 1px solid">{<br>&emsp;<span title="created great id"><ins>id: 343</ins></span><br>}</td>
                <td style="border: 1px solid">the user_id<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Patch</i> api/article/:greatId<br><br>{<br>&emsp;great: -1<br>}</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the user_id of the great<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Delete</i> api/article/great/:greatId</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the user_id of the great<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
        </tbody>
    </table>
</details>


<details>
  <summary>Comment API</summary>
    <table style="border-collapse: collapse;">        
        <thead style="background-color:gray; color:lightGray;">
            <tr>                
                <th style="border: 1px solid;">Request</th>
                <th style="border: 1px solid;">Response</th>
                <th style="border: 1px solid">Access constraint</th>
            </tr>
        </thead>
        <tbody style="white-space: pre; color: white;">
            <tr>
                <td style="border: 1px solid;"><i>Get</i> api/article/:articleId/comment</td>
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;article_id: 817,<br>&emsp;&emsp;like_count: 10,<br>&emsp;&emsp;user_id: 217,<br>&emsp;&emsp;update_at: null,<br>&emsp;&emsp;comment: "test",<br>&emsp;&emsp;id: 755,<br>&emsp;&emsp;<span title="this value has set when comment is reply"><ins>comment_id: null</ins></span>,<br>&emsp;&emsp;create_at: 1789013700000,<br>&emsp;&emsp;dislike_count: 30<br>&emsp;}<br>]</td>
                <td style="border: 1px solid">the userId<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid;"><i>Get</i> api/comment?<br>id=3,5,6</td>
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;article_id: 817,<br>&emsp;&emsp;like_count: 10,<br>&emsp;&emsp;user_id: 217,<br>&emsp;&emsp;update_at: null,<br>&emsp;&emsp;comment: "test",<br>&emsp;&emsp;id: 5,<br>&emsp;&emsp;<span title="this value has set when comment is reply"><ins>comment_id: null</ins></span>,<br>&emsp;&emsp;create_at: 1789013700000,<br>&emsp;&emsp;dislike_count: 30<br>&emsp;}<br>]</td>
                <td style="border: 1px solid">the userId<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/comment<br><br>{<br>&emsp;comment: 'good',<br>&emsp;user_id: 10,<br>&emsp;<span title="this value has set when comment is reply"><ins>comment_id: 55,</ins></span><br>&emsp;article_id: 15<br>}</td>
                <td style="border: 1px solid">{<br><div title="created comment id">&emsp;<ins>id: 30</ins></div>}</td>
                <td style="border: 1px solid">the user_id<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Put</i> api/comment/:commentId<br><br>{<br>&emsp;comment: 'change comment'<br>}</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the user_id of the comment<br>and<br>authenticated user_id <br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Delete</i> api/comment/:commentId</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the user_id of the comment<br>and<br>authenticated user_id <br>must be equal</td>
            </tr>
        </tbody>
    </table>
</details>

<details>
  <summary>Comment Great API</summary>
    <table style="border-collapse: collapse;">        
        <thead style="background-color:gray; color:lightGray;">
            <tr>
                <th style="border: 1px solid">Request</th>
                <th style="border: 1px solid">Response</th>
                <th style="border: 1px solid">Access constraint</th>
            </tr>
        </thead>
        <tbody style="white-space: pre; color: white;">
            <tr>
                <td style="border: 1px solid"><i>Get</i> api/comment/great?<br><span title="essential"><ins>user_id=217</ins></span>&<br>article_id=817&<br>comment_id=755</td>
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;id:588,<br>&emsp;&emsp;article_id:817,<br>&emsp;&emsp;comment_id:755,<br>&emsp;&emsp;user_id:217,<br>&emsp;&emsp;great:1<br>&emsp;}<br>]</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/comment/great<br><br>{<br>&emsp;user_id: 30,<br>&emsp;comment_id: 70,<br>&emsp;<span title="-1 means disgreat, 1 means  great"><ins>great: -1</ins></span><br>}</td>
                <td style="border: 1px solid">{<br>&emsp;<span title="created great id"><ins>id: 343</ins></span><br>}</td>
                <td style="border: 1px solid">the user_id<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Patch</i> api/comment/:greatId<br><br>{<br>&emsp;great: 1<br>}</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the user_id of the great<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Delete</i> api/comment/great/:greatId</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the user_id of the great<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
        </tbody>
    </table>
</details>


<details>
  <summary>Blog API</summary>
    <table style="border-collapse: collapse;">        
        <thead style="background-color:gray; color:lightGray;">
            <tr>
                <th style="border: 1px solid">Request</th>
                <th style="border: 1px solid">Response</th>
                <th style="border: 1px solid">Access constraint</th>
            </tr>
        </thead>
        <tbody style="white-space: pre; color: white;">
            <tr>
                <td style="border: 1px solid"><i>Get</i> api/blog/:blogId</td>
                <td style="border: 1px solid">{<br>&emsp;image: 'https://e.webp',<br>&emsp;<span title="blog's ower"><ins>user_id: 217,</ins></span><br>&emsp;id: 36,<br>&emsp;title: 'my story'<br>}</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Get</i> api/blog?<br>id=4,5,6</td>
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;image: 'https://c.webp',<br>&emsp;&emsp;user_id: 218,<br>&emsp;&emsp;id: 4,<br>&emsp;&emsp;title: 'learning cook'<br>&emsp;}<br>]</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Patch</i> api/blog/:blogId<br><br>{<br>&emsp;title: 'new title',<br>&emsp;image: 'https://c.webp'<br>}</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the blogId<br>and<br>authenticated blog_id<br>must be equal</td>
            </tr>
        </tbody>
    </table>
</details>

<details>
  <summary>Category API</summary>
    <table style="border-collapse: collapse;">        
        <thead style="background-color:gray; color:lightGray;">
            <tr>
                <th style="border: 1px solid">Request</th>
                <th style="border: 1px solid">Response</th>
                <th style="border: 1px solid">Access constraint</th>
            </tr>
        </thead>
        <tbody style="white-space: pre; color: white;">
            <tr>
                <td style="border: 1px solid"><i>Get</i> api/category/:categoryId</td>
                <td style="border: 1px solid">{<br>&emsp;blog_id:39,<br>&emsp;name:'trip',<br>&emsp;id:210<br>}</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Get</i> api/blog/:blogId/category</td>
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;blog_id:39,<br>&emsp;&emsp;article_count:20,<br>&emsp;&emsp;name:'cook',<br>&emsp;&emsp;id:210<br>&emsp;}<br>]</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/category<br><br>{<br>&emsp;name: 'education',<br>&emsp;blog_id: 70<br>}</td>
                <td style="border: 1px solid">{<br>&emsp;<span title="created category id"><ins>id: 343</ins></span><br>}</td>
                <td style="border: 1px solid">the blog_id<br>and<br>authenticated blog_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Patch</i> api/category/:categoryId<br><br>{<br>&emsp;name:'daily'<br>}</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the blog_id of the category<br>and<br>authenticated blog__id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Delete</i> api/category/:categoryId</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the blog_id of the category<br>and<br>authenticated blog__id<br>must be equal</td>
            </tr>
        </tbody>
    </table>
</details>


<details>
  <summary>Bookmark API</summary>
    <table style="border-collapse: collapse;">        
        <thead style="background-color:gray; color:lightGray;">
            <tr>
                <th style="border: 1px solid">Request</th>
                <th style="border: 1px solid">Response</th>
                <th style="border: 1px solid">Access constraint</th>
            </tr>
        </thead>
        <tbody style="white-space: pre; color: white;">
            <tr>
                <td style="border: 1px solid"><i>Get</i> api/user/:userId/bookmark?<br>article_id=4</td>
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;article_id: 4,<br>&emsp;&emsp;user_id: 217,<br>&emsp;&emsp;id: 156,<br>&emsp;&emsp;create_at: 1788159273000<br>&emsp;}<br>]</td>
                <td style="border: 1px solid">permit all</td>
            </tr>            
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/bookmark<br><br>{<br>&emsp;user_id: 30,<br>&emsp;article_id: 70<br>}</td>
                <td style="border: 1px solid">{<br>&emsp;<span title="created bookmark id"><ins>id: 343</ins></span><br>}</td>
                <td style="border: 1px solid">the user_id<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Delete</i> api/bookmark/:bookmarkId</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the user_id of the bookmark<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
        </tbody>
    </table>
</details>


<details>
  <summary>Alarm API</summary>
    <table style="border-collapse: collapse;">        
        <thead style="background-color:gray; color:lightGray;">
            <tr>                
                <th style="border: 1px solid;">Request</th>
                <th style="border: 1px solid;">Response</th>
                <th style="border: 1px solid">Access constraint</th>
            </tr>
        </thead>
        <tbody style="white-space: pre; color: white;">
            <tr>
                <td style="border: 1px solid;"><i>Get</i> api/user/:userId/alarm</td>
                <td style="border: 1px solid">[<br>&emsp;{<br>&emsp;&emsp;article_id: 561,<br>&emsp;&emsp;blog_id: 37,<br>&emsp;&emsp;to_user_id: 218,<br>&emsp;&emsp;checked: 1,<br>&emsp;&emsp;comment: 'Very good',<br>&emsp;&emsp;id: 134,<br>&emsp;&emsp;<span title="or 'REPLY', 'MENTION'"><ins>type: 'COMMENT',</ins></span><br>&emsp;&emsp;create_at: 1787822522000,<br>&emsp;&emsp;comment_id: 724,<br>&emsp;&emsp;from_user_id: 217<br>&emsp;}<br>]</td>
                <td style="border: 1px solid">the userId<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/alarm<br><br>{<br>&emsp;to_user_id: 3,<br>&emsp;from_user_id: 10,<br>&emsp;<span title="or 'REPLY', 'MENTION'"><ins>type: 'COMMENT',</ins></span><br>&emsp;comment_id: 15<br>}</td>
                <td style="border: 1px solid">{<br><div title="created alarm id">&emsp;<ins>id: 30</ins></div>}</td>
                <td style="border: 1px solid">the from_user_id<br>and<br>authenticated user_id<br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Patch</i> api/alarm/:alarmId<br><br>{<br>&emsp;checked: 1<br>}</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the to_user_id of the alarm<br>and<br>authenticated user_id <br>must be equal</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Delete</i> api/alarm/:alarmId</td>
                <td style="border: 1px solid"></td>
                <td style="border: 1px solid">the to_user_id of the alarm<br>and<br>authenticated user_id <br>must be equal</td>
            </tr>
        </tbody>
    </table>
</details>

<details>
  <summary>Blob API</summary>
    <table style="border-collapse: collapse;">        
        <thead style="background-color:gray; color:lightGray;">
            <tr>
                <th style="border: 1px solid">Request</th>
                <th style="border: 1px solid">Response</th>
                <th style="border: 1px solid">Access constraint</th>
            </tr>
        </thead>
        <tbody style="white-space: pre; color: white;">
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/blog/article<br><br>Content-Type: image/png<br>filename="blob"<br>name="image";</td>
                <td style="border: 1px solid">{<br>&emsp;id: ':imageId'<br>}</td>
                <td style="border: 1px solid">require authentication</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Get</i> /api/blob/article/:imageId</td>
                <td style="border: 1px solid">image blob</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/blog/profile<br><br>Content-Type: image/png<br>filename="blob"<br>name="image";</td>
                <td style="border: 1px solid">{<br>&emsp;id: ':imageId'<br>}</td>
                <td style="border: 1px solid">require authentication</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Get</i> /api/blob/profile/:imageId?<br><span title="support size: 256x256, 128x128, 96x96, 64x64, 48x48, 32x32, 24x24"><ins>size=96x96</ins></span></td>
                <td style="border: 1px solid">image blob</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/blob/article/thumbnail<br><br>Content-Type: image/png<br>filename="blob"<br>name="image";</td>
                <td style="border: 1px solid">{<br>&emsp;id: ':imageId'<br>}</td>
                <td style="border: 1px solid">require authentication</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Get</i> /api/blob/article/thumbnail/:imageId?<br><span title="support size: 960x960, 960x540, 512x320, 320x256, 540x960, 170x170, 160x128, 96x96"><ins>size=512x320</ins></span></td>
                <td style="border: 1px solid">image blob</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Post</i> api/blob/blog/image<br><br>Content-Type: image/png<br>filename="blob"<br>name="image";</td>
                <td style="border: 1px solid">{<br>&emsp;id: ':imageId'<br>}</td>
                <td style="border: 1px solid">require authentication</td>
            </tr>
            <tr>
                <td style="border: 1px solid"><i>Get</i> /api/blob/blog/image/:imageId?<br><span title="support size: 1920x1080, 960x540, 1920x168, 960x84"><ins>size=960x540</ins></span></td>
                <td style="border: 1px solid">image blob</td>
                <td style="border: 1px solid">permit all</td>
            </tr>
        </tbody>
    </table>
</details>
