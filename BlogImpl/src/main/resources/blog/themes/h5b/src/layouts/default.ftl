<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--social_media_metadata-->
        
        <link rel="stylesheet" href="css/normalize.min.css">
        <link rel="stylesheet" href="css/main.css">

        <script src="js/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
        
        <title>
            <#if post??>
                <#if post.title??>
                    ${post.title }
                </#if> 
                <#if site.title??> - </#if>
            <#elseif posts??>
                <#if category??>
                    Posts tagged '${category}'
                <#else>
                    All posts
                </#if>
                <#if site.title??> - </#if>
            </#if>
            <#if site.title??>${site.title}</#if>
            <#if site.subtitle??> - ${site.subtitle}</#if>
        </title>
    </head>
    <body>
        <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->

        <!-- header and navbar -->
        <div class="header-container">
            <header class="wrapper clearfix">
               <#if site.title??>
                   <h1 class="title">
                       <a href="${site.blogRoot}/">${site.title}
                           <#if site.subtitle??>
                               <br>
                               <small>${site.subtitle}</small>
                           </#if>
                       </a>
                   </h1>
               </#if>
            </header>
        </div>
        <!-- end header and navbar -->
        
        <!-- main body -->
        <div class="main-container">
            <div class="main wrapper clearfix">
                <#if post??>
                    <article>
                        <#if post.title??>
                             <h1> 
                                 <a href="${site.blogRoot}/${post.link}">
                                     <#if post.isDraftPost()>
                                        DRAFT - 
                                     </#if>
                                     <#if post.isPrivatePost()>
                                        PRIVATE - 
                                     </#if>
                                     ${post.title}
                                 </a>
                             </h1>
                         </#if>
                        <#if post.bannerImage??>
                            <img src="${post.bannerImage}" <#if post.title??>alt="${post.title}" title="${post.title}"</#if> class="banner_image" />
                        </#if>
                        <#if post.html??>
                            ${post.html}
                        </#if>
                        
                        <#if categories??>
                             <div class='category-links'>Posted in
                                 <#list categories as category>
                                     &bull; <a href="${category.url}">${category.name}</a>
                                 </#list>
                             </di>
                         </#if>
                    </article>
                <#elseif posts??>
                    <#list posts as post>
                        <div class="post-link">
                            <#if post.title??>
                                <h1> 
                                     <a href="${site.blogRoot}/${post.link}">
                                         <#if post.isDraftPost()>
                                            DRAFT - 
                                         </#if>
                                         <#if post.isPrivatePost()>
                                            PRIVATE - 
                                         </#if>
                                         ${post.title}
                                     </a>
                                 </h1>
                            </#if>
                    
                            <#if authors??>
                                <#assign authorId = post.authorID>
                                <#if authors[ authorId ]??>
                                    <#assign author = authors[ authorId ]>
                                    <span class="byline">posted by ${author.displayName}</span>
                                    <#if post.date??>
                                        <span class="byline">on ${post.date}</span>
                                    </#if>
                                </#if>
                            </#if>
                            
                            <#if post.pullQuote??>
                                <div class="pullquote">
                                    ${post.pullQuote}
                                </div>
                            </#if>
                            
                            <#if post.isPaginated()>
                                <a href="${site.blogRoot}/${post.link}">Read more...</a>
                            <#else>
                                <a href="${site.blogRoot}/${post.link}">Permalink</a>
                            </#if>
                        </div>
                    </#list>
                </#if>
                
                <#if navigation??>
                    <div class="site-navigation">
                        <#if navigation.previous??>
                            <a href="${navigation.previous}">&lt;&lt; Previous Page</a>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </#if>
                        
                        <#if navigation.next??>
                            <a href="${navigation.next}">Next Page &gt;&gt;</a>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </#if>
                        
                        <#if navigation.unpaginated??>
                            <a href="${navigation.unpaginated}">Full Article</a>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </#if>
                        
                        <#if navigation.permalink??>
                            <a href="${navigation.permalink}">Permalink</a>
                        </#if>
                    </div>
                </#if>
            </div>
        </div>
        <!-- end main body -->
        
        <!-- footer -->
        <div class="footer-container">
            <footer class="wrapper">
                Copyright &copy;
                All Rights Reserved &bull;
                <navigation>
                    <a href="${site.blogRoot}/">Home</a> &bull;
                    <a href="/${site.history}/1">All posts</a> &bull;
                </navigation>
            </footer>
        </div>
        <!-- end footer -->
        
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.11.2.min.js"><\/script>')</script>
        <script src="js/main.js"></script>
    </body>
</html>
